# Spring Security

**Spring Security** is a framework that provides authentication, authorization, and protection against common attacks.
Also, provides integration with other libraries to simplify its usage.

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

It allows more than one `SecurityFilterChain` bean. In this case, `FilterChainProxy` can be used to determine which `SecurityFilterChain` should be used, find the first chain that matches to the request. 
This allows providing a separate configuration for different slices of the application, when each chain can have the numbers of filters whatever is needed.

![alt text](https://docs.spring.io/spring-security/site/docs/5.4.1/reference/html5/images/servlet/architecture/multi-securityfilterchain.png "Multiple Security Filter Chain")
      
### Security Filters

_Spring Security_ has a lot of filters, that can be use for many situations. Some examples are **CsrfFilter**, **OAuth2AuthorizationRequestRedirectFilter** and **BasicAuthenticationFilter**. 
Of course, we don't need to use them all. They can be configured according applications' necessity, and as we said, we can have different groups of filters.

Again, the order of filters matters, and the filters provided by _Spring Security_ has they own order. 
It isn't necessary know this ordering, however there are times that it is beneficial to know this.

## Authentication

**Authentication** is when you prove your identity, and _Spring Security_ provides comprehensive support for this. Let's see the main architectural components of _Spring Security's_ used in _Servlet_ authentication.

* **SecurityContextHolder:** the heart of _Spring Security's_ authentication model. It's here that the details of who is authenticated is store. 
It doesn't matter how is populated, if it contains a value, then it is used as the currently authenticated user.

* **SecurityContext:**  it's obtained from `SecurityContextHolder` and contains the  `Authentication` of the currently authenticated user. It's always available to methods in the same thread.

* **Authentication:** can be input in an `AuthenticationManager` to provide the credentials to authenticate or, provide the current user from the `SecurityContext`. Contains three objects: principal, credentials and authorities.

* **Granted Authority:** authorities that is granted to the principal and use for authorization, like roles and scopes.

* **AuthenticationManager:** defines how Spring Security's Filters perform authentication. It's not mandatory to use and the most common implementaion is `ProviderManager`.

* **AuthenticationProvider:** object that try the authentication. Usually, `AuthenticationManager` has a list of providers and each provider try to authenticated, until one has success.

* **AuthenticationEntryPoint:** used to send an HTTP response that request credentials from a client, when the client try to make an unauthenticated request in a secret resource. 

* **AbstractAuthenticationProcessingFilter:** a base `Filter` used for authentication, and has differents subclass for different types of requests.

## Username/Password Authentication

One of the most common ways to authenticate a user is validating a username and password.To read more,see [username and password](username-password-authentication)

## Authorization

The advanced authorization capabilities within Spring Security represent one of the most compelling reasons for its popularity. See [authorization](authorization) to read more about it.

## OAuth 2.0

OAuth is an open protocol to allow secure authorization in a simple and standard method from web, mobile and desktop applications. Read more in [oauth](oauth) directory.


    
