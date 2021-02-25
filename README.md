# Spring Security

**Spring Security** is a framework that provides authentication, authorization, and protection against common attacks. This framework supports both imperative and reactive applications, and requires Java 8 or higher.

## Servlet Applications

Spring Security's Servlet support is based on **Servlet Filter**, there are objects that can intercept HTTP requests sent to your web application. When a request is received, the container creates a sequence called `FilterChain`, which contains the filters and Servlet that must process the request, based on the path of the request's URI. In a Spring MVC application the Servlet is an instance of `DispacherServlet`. At most one _Servlet_ can handle a single request and response, but more than one _Filter_ can be used to change, validate or even reject the request. The order of filters are extremely important, because a filter only impacts downstream filters and the Servlet.

![alt text](https://docs.spring.io/spring-security/site/docs/5.4.1/reference/html5/images/servlet/architecture/filterchain.png "Filter Chain")

Servlet container does not reconize Spring's Beans. So `DelegatingFilterProxy`, an implementation of _Servlet Filter_, allows a bridge between the _Servlet_ container's lifecycle and Spring's _Application Context_. It's registred in the _Servlet_ container and delegates the work to a Spring Bean that implements Filter interface.

The bean used by `DelegatingFilterProxy` is the `FilterChainProxy`, that is a special _Filter_ provided by the Spring Security. This filter delegates the request to many _Filters_ instances through `SecurityFilterChain`, that determines which _Spring Security Filter_ should be invoked for this request.

![alt text](https://docs.spring.io/spring-security/site/docs/5.4.1/reference/html5/images/servlet/architecture/securityfilterchain.png "Sequence show DelegatingFilterProxy, his FilterChainProxy and the SecurityFilterChain")

We can have more than one `SecurityFilterChain`, and `FilterChainProxy` is used to determine which one will proccess the request, finding the first one that is enabled, based for example in the URI. This allows it provide a separate configuration for different parts of the application, when each chain can have as many filters as needed.

![alt text](https://docs.spring.io/spring-security/site/docs/5.4.1/reference/html5/images/servlet/architecture/multi-securityfilterchain.png "Multiple Security Filter Chain")

The `SecurityFilterChain` is made up of **Security Filters**, that are beans that proccess the request and actually do the job. _Spring Security_ has a many filters, as **CsrfFilter**, **OAuth2AuthorizationRequestRedirectFilter** and **BasicAuthenticationFilter**, that can be configured according application's necessity.

The filters provided by _Spring Security_ has they own order, and this order also important. It isn't necessary know this order, but there are times when it is beneficial to know this.

## Authentication

**Authentication** is when you prove your identity, and _Spring Security_ provides comprehensive support for this. Let's see the main architectural components of _Spring Security's_ used in _Servlet_ authentication.

* **SecurityContextHolder:** the heart of _Spring Security's_ authentication model, where the details about authenticate user is store. Spring Security doesn't care how is populated, if it contains a value, then it is used as the currently authenticated user. 

* **SecurityContext:**  it's obtained from `SecurityContextHolder` and contains the `Authentication` object. By default, a _ThreadLocal_ is used to store user authenticate details, which means that the `SecurityContext` is always available to methods in the same thread. However, this can be change in configurations.

* **Authentication:** this object server two purposes. The first is an input to `AuthenticationManager` to provide the credentials a user has provided to authenticate. The second is represents the currently authenticated user, that can be obtained from the `SecurityContext`. The `Authentication` object contains three properties: principal, credentials and authorities

* **Granted Authority:** authorities that is granted to the principal and use for authorization, like roles and scopes.

* **AuthenticationManager:** defines how Spring Security's Filters perform authentication. It's not mandatory to use and the most common implementaion is `ProviderManager`. 

* **AuthenticationProvider:** object that try the authentication. Usually, `AuthenticationManager` has a list of providers and each provider try to authenticated, until one has success. In practice each `AuthenticationProvider` knows how to perform a specific type of authentiction. 

* **AuthenticationEntryPoint:** used to send an HTTP response that request credentials from a client, when the client try to make an unauthenticated request in a secret resource. 

* **AbstractAuthenticationProcessingFilter:** a base `Filter` used for authentication, and has differents subclass for different types of requests.

## Username/Password Authentication

One of the most common ways to authenticate a user is validating a username and password.To read more,see [username and password](username-password-authentication)

## Authorization

The advanced authorization capabilities within Spring Security represent one of the most compelling reasons for its popularity. See [authorization](authorization) to read more about it.

## OAuth 2.0

OAuth is an open protocol to allow secure authorization in a simple and standard method from web, mobile and desktop applications. Read more in [oauth](oauth) directory.


    
