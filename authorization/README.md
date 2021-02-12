# Authorization

**Authorization** is when the system check what the authenticated user can do. _Spring Security_ provides support for this, working with **Authorities**.

_Authorities_ can be a role, permission or scope. The `Authentication` object stores a list of `GrantedAuthority` interface, which represents the authorities that have been grated to the principal (user).
The list is inserted into the `Authentiction` by the `AuthenticationManager` and later read by `AccessDecisionManager` when making authorization decisions.
_Spring Security_ includes one concrete `GrantedAuthority` implementation, `SimpleGrantedAuthority`.  

### AccessDecisionManager

We mention earlier the `AccessDecisionManager` interface, that is used for _Spring Security_ to control access to secure objects. 
`AccessDecisionManager` is called by `AbstractSecurityInterceptor`, and is responsible for making final access control decision. 
This interface contains three methods, that are used to receive the relevant information and determine if the class can validate the request.

We can implement our own `AccessDecisionManager`, but _Spring Security_ includes several implementations that are based on voting. 
Using this approach, a series of `AccessDecisionVoter` implementations are polled on an authorization decision. 
Each voter implementation can return a value with their vote, with each voter having their own logical for defining their vote.

The voter's return type is an int value and can be represented with the static fields: 
* ACCESS_ABSTAIN: when it has no opinion on an authorization decision 
* ACCESS_DENIED: when vote to deny the authorization
* ACCESS_GRANTED: when vote to allow the authorization
  
Based on its assessment of votes, the `AccessDecisionManager` decides if throw an `AccessDeniedException` or authorize the request to be executed. 

## Authorize Requests

The **FilterSecurityInterceptor** provides authorization for requests. This object is inserted into the _FilterChainProxy_ as one of the _Security Filters_. 
It's responsible for extracting user information and call `AccessDecisionManager`.

For this work correctly, we need to config which route should require an authentication user. 
By default, Spring Security authorization will require all requests need authentication. However, we can configure to have different rules for different routes.

```
protected void configure(HttpSecurity http) throws Exception {
    http
        // ...
        .authorizeRequests(authorize -> authorize                                  
            .mvcMatchers("/resources/**", "/signup", "/about").permitAll()                  
            .anyRequest().denyAll()                                                
        );
}
```

## Expression-Based Access Control

_Spring Security_ 3.0 introduced the ability to use **Spring EL** expression as an authorization mechanism in addition to the simple use of configuration attributes and access-decision voters. 
Expression-based access control is built on the same architecture but allows complicated Boolean logic to be encapsulated in a single expression.

Expressions are evaluated with a "root object" as part of the evaluation context. _Spring Security_ uses specific classes for web and method security as the root object, 
in order to provide built-in expressions and access to values such as the current principal.

The base class for expression root objects is `SecurityExpressionRoot`, and provide some common expression which are available in both web and method security, like **hasRole**, **hasAuthority**, **denyAll** and **permitAll**.

Additionally, we have specific root object to web security, defined by the `WebSecurityExpressionRoot` class, which is use when evaluating web-access expression. 
`WebSecurityExpressionRoot` class add the **hasIpAdrdress** method and provide directly access to `HttpServletRequest` object. 
Besides that, a `WebExpressionVoter` will be added to the `AccessDecisionManager`.

## Beans and path variable with expression

We can easily extend the expressions available using an exposed Spring Bean. 
With the bean name, defined explicitly ou implicitly, we can refer to the object and invoke a method to do the validation.

```
public class WebSecurity {
        public boolean check(Authentication authentication, HttpServletRequest request) {
                ...
        }
}

http
    .authorizeRequests(authorize -> authorize
        .antMatchers("/user/**").access("@webSecurity.check(authentication,request)")
        ...
    )

```

Likewise, when the URL has a path variable, we can easily refer to it and pass the variable's value to the method.

```
/* called URL = /users/{userId} */

public class WebSecurity {
        public boolean checkUserId(Authentication authentication, int id) {
                ...
        }
}

http
    .authorizeRequests(authorize -> authorize
        .antMatchers("/user/{userId}/**").access("@webSecurity.checkUserId(authentication,#userId)")
        ...
    );
```  

## Method Security Expressions

Method security support expression and allow a more specific validation of access, in addition to being able to filter the data that a user can access.
There are four annotations which support these actions, and work with pre or post invocation. 
Before use annotations, we have to enable them, and this could be done on any Spring Bean.

```
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
}
```   

### @PreAuthorize and @PostAuthorize

The most obviously annotation is `@PreAuthorize` which decides whether a method can actually be called or not, using an expression.
The expression defined within this annotation is executed before the method execution.

```
@PreAuthorize("hasAnyRole('ADMIN, TEACHER, COORDINATOR')")
public ResponseEntity<List<ClassEntity>> listClass() {}
```

Any Spring-EL functionality is available within the expression, so you can also access properties like _authentication_ and _principal_ on the arguments. 
`@PreAuthorize` also supports Spring Beans calls.

```
@PreAuthorize("@classSecurity.checkClassAccess(authentication, #id)")
public ResponseEntity<ClassEntity> getClass(@PathVariable("id") Integer id) {}
```

Another option is to use the method argument as part of the expression, doesn't matter the type of the argument.
There are a number of ways in which _Spring Security_ can resolve the method arguments, but since Java 8 and Spring 4, the most common is using the standard JDK reflection API.

The other annotation, `@PostAuthorize` works almost the same way, but executes after method invocation. For this reason, its use is less common.
Just as `@PreAuthorize` can access the method arguments, `@PostAuthorize` can access the return value from a method, use the built-in name **returnObject** in the expression.

### @PreFilter and PostFilter

_Spring Security_ supports filtering of collection, arrays, maps and streams using expressions. This is most commonly performed on the return value of a method. 

```
@PreAuthorize("hasRole('USER')")
@PostFilter("hasPermission(filterObject, 'read') or hasPermission(filterObject, 'admin')")
public List<Contact> getAll();
```

When use `@PostFilter` annotation, Spring Security iterates through the returned collection or map and removes any elements for which the supplied expression is false. 
The name **filterObject** refers to the current object in the collection. 
We can also filter before the method call, using `@PreFilter`, though this is a less common. 
The syntax is just the same, but if there is more than one argument which is a collection type then you have to select one by name using the `filterTarget` property of this annotation.

These two annotations also supports Spring Beans invokes.

```
@PreAuthorize("hasAnyRole('COORDINATOR, TEACHER')")
@PostFilter("@gradeSecurity.checkCanReadGrade(authentication, filterObject)")
public List<GradeEntity> listStudentClassGrades(@PathVariable("idClass") Integer idClass) {
    return repository.findClassGrades(idClass);
}
```

One important thing to pay attention to is that the method return need to be a collection. So, if you build a Rest API and return a Response object, this annotation won't work.
However, if you change to return a collection, as required, the Spring MVC still can understand and return the response correctly.