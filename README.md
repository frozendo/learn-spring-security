# Spring Security

**Spring Security** is a framework that provides authentication, authorization, and protection against common attacks.
Also, provides integration with other libraries to simplify its usage.

To work with _Spring Security_, it is good to have a clear idea about two concepts: 

* **Authentication:** It's about proving that I really am who I say I am. To do this, I need to use passwords or other ways to prove my identity.
_Spring Security_ provides built in support for authenticating users, be it a servlet or webflux application.

* **Authorization:** It's about determined what I can do in application. 
Similarity Authentication, _Spring Security_ have methods and features that helps determine and validate what a user can do.

## Spring Security and Spring Boot

When we use Spring Boot, it's automatically realize a default configuration, that basically does two things: 
* Creates a servlet _Filter_ as a bean named `springSecurityFilterChain`, responsible for all the security within your application, and register its in _Servlet Container_
* Create a bean of type `UserDetailsService`, with username **_user_** and a randomly generated password, printed in the console.

> **_Obs.:_** Spring Boot make things easily, but we don't need to use it. Like other Springs projects, it's possible use Spring Security without use Spring Boot.

## Spring Security with Servlet Applications

In this repository we focus in use _Spring Security_ with **Servlet Applications**. With this type of application, _Spring Security_ it'll work based on **Servlet Filter**.
 
When client sends a request to the application, the container creates a `FilterChain` and the `Servlet` that should process the `HttpServletRequest`, both things based on the path of the request URI.
In a Spring MVC application the _Servlet_ is an instance of `DispacherServlet`. 
At most one _Servlet_ can handle a single request and response, but more than one _Filter_ can be used to change, validate or even reject the request. 
This sequence of filters known `FilterChain`, and the order of filters in this chain are extremely important. 

![alt text](https://docs.spring.io/spring-security/site/docs/5.4.1/reference/html5/images/servlet/architecture/filterchain.png "Filter Chain")

### Servlets Filters

First of all, Spring provides a _Filter_ implementation named `DelegatingFilterProxy` that allows bridging between the _Servlet_ container's lifecycle and Spring's _Application Context_, since _Servlet_ container not aware Spring's Beans. 
`DelegatingFiterProxy` can be registered via standard _Servlet_ container mechanisms and after delegates all the work to a Spring Bean that implements Filter interface.

Usually the bean wrapped in a `DelegatingFilterProxy` is the `FilterChainProxy`, that is a special _Filter_ that allows delegating the request to many _Filter_ instances through `SecurityFilterChain`.
So, `SecurityFilterChain` is used by `FilterChainProxy` to determine which _Spring Security Filter_ should be invoked for the request.

![alt text](https://docs.spring.io/spring-security/site/docs/5.4.1/reference/html5/images/servlet/architecture/securityfilterchain.png "Sequence show DelegatingFilterProxy, his FilterChainProxy and the SecurityFilterChain")

With this architecture, _Spring Security_ has a starting point for all support, centralize some tasks and provide flexibility, determining invocation based on anything in the request.

It allows more than one `SecurityFilterChain` bean. In this case, `FilterChainProxy` can be used to determine which `SecurityFilterChain` should be used, find the first chain that matches to the request. 
This allows providing a separate configuration for different slices of the application, when each chain can have the numbers of filters whatever is needed.

![alt text](https://docs.spring.io/spring-security/site/docs/5.4.1/reference/html5/images/servlet/architecture/multi-securityfilterchain.png "Multiple Security Filter Chain")
      
#### Security Filters

_Spring Security_ has a lot of filters, that can be use for many situations. Some examples are **CsrfFilter**, **OAuth2AuthorizationRequestRedirectFilter** and **BasicAuthenticationFilter**. 
Of course, we don't need to use them all. They can be configured according applications' necessity, and as we said, we can have different groups of filters.

Again, the order of filters matters, and the filters provided by _Spring Security_ has they own order. 
It isn't necessary know this ordering, however there are times that it is beneficial to know this.

___  

To read more the completely documentation, access this [link](https://spring.io/projects/spring-security#learn).




    
