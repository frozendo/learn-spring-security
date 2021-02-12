# Username/Password Authentication

_Spring Security_ provides support for username/password authentication, with two mechanisms to do that: **Form Login** and **Basic Authentication**

> **Obs.:** Spring Security also provide support to Digest Authentication, but it's not recommended to use this mechanism in modern applications.

The two mechanisms work almost the same way. 

In both cases, when we receive an unauthenticated request for a private resource, _Spring Security_ denies this request and
return an implementation of `AuthenticationEntryPoint` to force the user login: `LoginUrlAuthenticationEntryPoint` for **FormLogin** and `BasicAuthenticationEntryPoint` for **BasicAuth**.  

When the username and password sent, a filter will process the user's authenticate: **FormLogin** uses `UsernamePasswordAuthenticationFilter` and **BasicAuth** uses `BasicAuthenticationFilter`.
Both create a `UsernamePasswordAuthenticationToken`, which is a type of `Authentication` and extracting the username and password from the request.
Then, the object passes to `AuthenticationManager` to be authenticated. The details of what `AuthenticationManager` looks like depend on how the user information is stored.

If authentication is successful, then the Authentication object inserted into the `SecurityContextHolder`, and the success status returned. 
If failure, the `SecurityContextHolder` is cleared out, and the failure returned to user.

## Form Login

_Form Login_ is enabled by default for servlet application, if any other configuration is not provided. 
However, it can be explicitly provided with minimal configuration. 
This can be done by creating a class extends `WebSecurityConfigurerAdapter` and override the _configure_ method, that have an HttpSecurity object.

```
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) {
        http.formLogin();
    }
}
```

With this minimal configuration, _Spring Security_ will render a default form login and will be responsible for doing the entire authentication flow.

In most cases, applications have their own login form, and we can allow this with an extra simple configuration. 

```
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) {
        http.formLogin().loginPage("/login").permitAll();
    }
}
```

The `loginPage` method receives the name of the login page, since this page can be found in the application's classpath.
With a login page specified, you are responsible for rendering the page. To do this, we need provide some key points: 

* The form should perform a `post` call to `/login` URL
* The form should specify the username in a parameter named `username`
* The form should specify the password in a parameter named `password`
* The form will need to include a CSRF Token
* If the HTTP parameter error found, it indicates the user failed to provide a valid username/password
* If the HTTP parameter logout is found, it indicates the user has logged out successfully

If it's necessary to customize anything of the points above, this can be done with additional configurations.
  
 ## Basic Authentication

Like **FormLogin**, **BasicAuthentication** is enabled by default and only needs to be provided explicitly when another servlet based configuration is provided.
   
Again, this can be done by creating a class that extends `WebSecurityConfigurerAdapter`.

```
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) {
        http.httpBasic();
    }
}
```

In some cases we want to change the returned made by _Spring Security_ when the user is not authenticated.
For these situations, we can create a class that implements `AuthenticationEntryPoint` and pass an instance of this class to the method `authenticationEntryPoint`.

```
 http.httpBasic().authenticationEntryPoint(customAuthenticationEntryPoint);
```

## Store Credentials

_Spring Security_ provides different ways to store, retrieve and validate user credentials. 

### DaoAuthenticationProvider

First of all, we need to look at **DaoAuthenticationProvider**. This is an implementation of `AuthenticationProvider`, so it's used by `AuthenticationManager` to validate the username and password.
This is the class responsible to managing the authentication process and then return a result to the `AuthenticationManager`.

To do this, it's uses a `UserDetailsService` for retrieving a username, password and other attributes for the authenticating username and password.
 `UserDetailsService` return an `UserDetails` object, that is validated by `DaoAuthenticationProvider`, with help of `PasswordEncoder`, and will be introduced like a principal in `Authentication` object, if authentication is successful.  

### InMemory Authentication

**InMemory Authentication** is one of the options that Spring provide for storing user information. 
When we use it, user information is kept together with the source code and when the application starts, this data is loaded into application memory.
This is not a good option for production, since everyone can obtain and decompiler the code, and thus get access to sensitive data.
_InMemory Authentication_ is the default option for storing user information. 

To retrieved data in memory, _Spring Security_ uses `InMemoryUserDetailsManager` an implementation of `UserDetailsManager`.
We can create users by creating a `UserDetailsService` bean, using the `UserBuilder` class to build the objects and return an instance of `InMemoryUserDetailsManager`, with the users created.

```
@Bean
public UserDetailsService users() {
    // The builder will ensure the passwords are encoded before saving in memory
    UserBuilder users = User.withDefaultPasswordEncoder();
    UserDetails user = users
        .username("user")
        .password("password")
        .roles("USER")
        .build();
    UserDetails admin = users
        .username("admin")
        .password("password")
        .roles("USER", "ADMIN")
        .build();
    return new InMemoryUserDetailsManager(user, admin);
}
```

Another option is to override the method `configure` of the `WebSecurityConfigurerAdapter` class, which has an `AuthenticationManagerBuilder` object. Then, using the builders methods, we can create and define the user data.

```
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
            .withUser("admin")
            .password(passwordEncoder().encode("admin")).roles("ADMIN")
            .and()
            .withUser("user")
            .password(passwordEncoder().encode("user")).roles("USER");
}
```

### JDBC Authentication

**JDBC Authentication** allows us to have the user data stored in a database. This is a better option to production environment, as the sensitive data is no longer kept in the code.
In this case, _Spring Security_ uses `JdbcDaoImpl` class, a different implementation for `UserDetailsService`.

To activate this option, in the class that extends the `WebSecurityConfigurerAdapter`, override `configure` method and use the `jdbcAuthentication` method of the `AuthenticationManagerBuilder` object.

```
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource());
    }
}
```

`JdbcDaoImpl` require tables to load information about the user, and we have the option of using a default schema, provided by _Spring Security_, with two tables.

```
create table users(
    username varchar_ignorecase(50) not null primary key,
    password varchar_ignorecase(500) not null,
    enabled boolean not null
);

create table authorities (
    username varchar_ignorecase(50) not null,
    authority varchar_ignorecase(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);
```

It's important we configure the dataSource correctly, or `JdbcDaoImpl` can't connect to the database. In example below, we set up a dataSource to embedded database.

```
@Bean
DataSource dataSource() {
    return new EmbeddedDatabaseBuilder()
        .setType(H2)
        .addScript("classpath:org/springframework/security/core/userdetails/jdbc/users.ddl")
        .build();
}
```

We don't need to make extras configurations, as `JdbcDaoImpl` is prepared to access these tables when authenticated the user. 
However, when necessary, we can provide our own table structure. In these cases, we have to provide a custom implementation of the `UserDetailsService` class, and override the method `loadUserByUsername`.
In this method we build a logic to access and retrieve data from our database.

```
@Service
public class CustomUserDetails implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //logic to get user data
    }
}

public class JdbcCustomDatabase extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("customUserDetails")
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

}
```  

### PasswordEncoder

**PasswordEncoder** is used to perform one way transformation of a password to allow the password to be stored securely, and used when it needs to be compared to a password provides by the user at the time of authentication.
_Spring Security_ provides different implementations of `PasswordEncoder`, that provide supports to different encoder algorithms, like: 

* **NoOpPasswordEncoder:** which required plain text passwords, in other words, without cryptography.
* **BCryptPasswordEncoder:** implementation that support **bcrypt** algorithm, the default implementation.
* **Argon2PasswordEncoder:** use **Argo2** algorithm to hash the passwords.
* **Pbkdf2PasswordEncoder:** this class give support to **PBKDF2** algorithm
* **SCryptPasswordEncoder:** class use **scrypt** algorithm to hash passwords 

We can define a `PasswordEnconder` bean with any of these class. So, when the user tries authenticate, the password given at the login will be encoded use this instance, and consequently the relationship algorithm.
For this work, it's important use the same algorithm to register the user's password.

```
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

When defining a bean for a specific class, all passwords must be stored with the same algorithm. Sometimes, especially when we have legacy data, it's necessary to support passwords in different forms of encryption.
To do this, we can use the `DelegationPasswordEncoder` class: 

```
PasswordEncoder passwordEncoder =
    PasswordEncoderFactories.createDelegatingPasswordEncoder();
```

Now, the password need to be stored in a specific format:

```
{id}encodedPassword
```

Such that `ìd` is an identifier used to look up which `PasswordEncoder` should be used, and `encodedPassword` is the original encoded password for the selected `PasswordEncoder`. 
Based on `id`, the correct implementation and algorithm will be used by the `DelegationPasswordEncoder` to verify if the password given by user in the form login is the correct password.
If the `id` cannot be found, the `id` will be null and if any implementation can't be applied for this id, an error will occur.