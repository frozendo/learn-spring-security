# Form Login

_Spring Security_ provides login support with a username and password provided through a html form. 
This technique is know as **FormLogin**.

When a user makes an unauthenticated request to a private resource, _Spring Security_ denies this request and sends a redirect to the login page.
This is usually done with the configured `LoginUrlAuthenticationEntryPoint`, an implementation of `AuthenticationEntryPoint`.

When the username and password sent, the `UsernamePasswordAuthenticationFilter` authenticates the user. 
First, it creates a `UsernamePasswordAuthenticationToken`, which is a type of `Authentication`, extracting the username and password from the `HttpServletRequest`.
Then, the object passes to `AuthenticationManager` to be authenticated. The details of what `AuthenticationManager` look like depend on how the user information is stored.

If authentication is successful, then the Authentication object inserted into the `SecurityContextHolder`, and the success status returned. 
If failure, the `SecurityContextHolder` is cleared out, and the failure returned to user.

_Form Login_ is enabled by default for servlet application, if any other configuration is not provided. 
However, it can be explicitly provided with minimal configuration. 
This can be done by creating a class extends `WebSecurityConfigurerAdapter` and override the _configure_ method, that have an HttpSecurity object.

```
protected void configure(HttpSecurity http) {
    http.formLogin();
}
```

With this minimal configuration, _Spring Security_ will render a default form login and will be responsible for doing the entire authentication flow.

In most cases, applications have their own login form, and we can allow this with an extra simple configuration. 

```
protected void configure(HttpSecurity http) {
    http.formLogin().loginPage("/login").permitAll();
}
```

The `loginPage` method receives the name of the login page, since this page can be found in the application's classpath.
With a login pag specified, you are responsible for rendering the page. To do this, we need provide some key points: 

* The form should perform a `post` call to `/login` URL
* The form should specify the username in a parameter named `username`
* The form should specify the password in a parameter named `password`
* The form will need to include a CSRF Token
* If the HTTP parameter error found, it indicates the user failed to provide a valid username/password
* If the HTTP parameter logout is found, it indicates the user has logged out successfully

If it's necessary to customize anything of the points above, this can be done with additional configurations.

Additionally, with Spring MVC is required add a controller that maps a **GET /login** endpoint to the login template. 

```
@Controller
class LoginController {
   @GetMapping("/login")
   String login() {
       return "login";
   }
}
```  

_Form Login_ can be used with each type of authentication provided by _Spring Security_, such as **InMemory Authentication** or **JDBC Authentiacation** 
  
 


