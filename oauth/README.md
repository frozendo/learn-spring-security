# OAuth2

## OAuth Framework

OAuth 2.0 is a framework that allows a user give temporary access to a third party application to their information.
In the traditional model, when a third party wants to access someone's resources in other applications, that third party application must have user credentials. 
This isn't a good scenario, because if the user needs to give access to more than one third party, their credentials have remained shared, which is a security issue. 
In addition, when the user changes his password, all third parties must be notified and update the credentials they store.

With OAuth 2.0 a user can provide third party access to his resource without have to share their credentials. 
In addition, they can limit the third party access, either by time or the information they can access.

## Roles

OAuth define four roles: 

* **resource owner:** entity capable of grating access to the protected resource.
* **resource server:** server hosting the protected resource
* **client:** application making protected resource requests, on behalf of the resource owner and with his authorization
* **authorization server:** server issuing access tokens to the client after successfully authenticating the resource owner and obtaining authorization.

This four agents interact with each other to ensure the best security for the protected resource. 

The resource server must trust the authorization server, and sometimes these two entities are represented by the same application. 
The authorization server may issue access tokens accepted by more than one resource server.

### Client registration

In most cases, clients need to register with the authorization server. This registration can be done in different ways, such as by direct interaction or by authorization server discovery client on a trusted channel. 

When registering, clients need to specify a type, based on their ability to maintain the confidentially of their client credentials and the authorization server's definitions. There are two types of clients: **confidential** and **public**.

After registering, clients will have a unique identifier, which is used by the authorization server to identify the client. 
With this identifier, know as **client_id**, clients must authenticate themselves in authorization server, to request the approval of the resource owner, and the access token to access protected resources. 
This authentication can be done through HTTP basic authentication or another similar mechanism. 
In addition to *client_id*, clients need a way to prove their identity, and this can be done by **client_secret**, when a password was given to the client, or by **redirect_uri**, which is an uri informed by the client during registration and can be validated when a request is made.

## Protocol Endpoints

The authorization process utilizes two authorization server endpoints and one client endpoint. Not all types of authorization grants use both endpoints.

### Authorization Endpoint 

This is a server endpoint that can be used by the client to obtain authorization from the resource owner via user-agent redirection.
Then, the resource owner is redirected to the authorization server, that is responsible to verifying the identity of the resource owner. 
The parameter *response_type* can be used to inform the authorization server of the desired grant type. This parameter could assume two values: 

* **code**: used in the authorization code flow, the endpoint will return a code that can be changed for an access token in another step
* **token**: used in the implicit flow, the endpoint will return an access token direct to the client, after authentication of the resource owner.

### Token Endpoint

The token endpoint is other server endpoint, used by the client to obtain an access token presenting its authorization grant or refresh token.  
The client need authentication to call this endpoint, so he needs to inform his credentials. 

### Redirection Endpoint

This is a client endpoint defined by Oauth, used by the authorization server to return responses containing authorization credentials to the client through the resource owner's user-agent, after completing its interactive with the resource owner. 
This endpoint need to be registered by the client during its registration in the authorization server.

## Scopes

When the client requests authorization for the resource owner through the authorization server, he needs to specify the scopes that he wants to access. 
Then the authorization server needs to ask the resource owner which scopes he allows the client access.
The resource owner can grant access to all scopes requested by the client, some of them or none.

These scopes are used to give right of access to protected resources. 
For example, the resource owner might give a client access to their information such as name and email, but refuse access to information such as phone and address.

It's important that the resource server knows about these scopes, and is able to expose to the client only what has been released by the resource owner.

## Access token and Refresh token

OAuth works with two types of token. 

**Access token** is token used by the client to access protected resource. 
This token must be sent to resource server whenever a request for a protected resource is made.
Access token is a short live token, which means that it expires after a short time. 
This time is set by the authorization server, but it's usually about 1 hour. 

**Refresh token** is a token used by the client to get new access token without the resource owner authenticating again.
This token is typically long-lasting credentials, which means that it's valid to a long period.
When the client knows that the access token will expire, or when a request return an error because that, the client can request a new token for the authorization server.
For this, he makes request to *token endpoint*, with two parameters:

* **grant_type:** type of the request, in this case the value must be set to **refresh_token**.
* **refresh_token:** a valid refresh token to authenticate in the authorization server .

The client also needs to send their credentials to validate in the authorization server. 
The authorization server will validate the client credentials, and the refresh token.
If everything is ok, the authorization server will return a new access token and sometimes a new refresh token.
If a new refresh token is returned, the old refresh token (used in the request) has become invalid.

## Grant Types

The authorization from resource owner to a client is expressed in the form of an authorization grant, which is a credential representing the resource owner's authorization and allow the client to request an access token. OAuth defines four grant types: authorization code, implicit, resource owner password credentials, and client credentials.

### Authorization Code

Authorization Code Grant is a redirection-based flow, so the client must be able to interact with resource owner's user-agent. 
In this flow, the resource owner's credentials are never shared with the client.

When authentication is required, the client directs the resource owner to an authorization server using the *authorization endpoint*, including some information: 

* **response type:** in authorization code the value must be set to **code**.
* **client_id:** client identifier in the authorization server
* **redirect_uri:** uri to redirect the resource owner after the authentication. This parameter is optional and if left blank, the authorization server tries to use the **redirect_uri** informed by the client during its registration.
* **scope:** scope requested by the client. This parameter is optional and if left blank, the authorization server will use its default scopes.
* **state:** a value used by the client to maintain state between the request and the callback. This will be included by authorization server when redirecting the user-agent back to the client. It's not mandatory, but should be used for security reasons.

When receives the request, the authorization server authenticates the resource owner and obtains its authorization for scopes requested by the client. 
Assuming that the resource owner can authenticate and grant access, the authorization server directs the resource owner back to the client using the *redirect_uri*. 
Again, some information is included:

* **code:** authorization code generated by the authorization server.
* **state** value informed by the client when requesting it. If the client informed the value, then the authorization server must include this value in response.

So, the client receives the response and makes another request to the *token endpoint*. Now the user is not redirecting, because this process occurs in background.
To make a request to token endpoint, we use some parameters: 

* **grant_type:** in authorization code the value must be set to **authorization_code**.
* **code:** the authorization code received from the authorization server.
* **redirect_uri:** if redirect_uri was included in the authorization request, this value is mandatory and must be identical to the value used previously.

When making this request, the client need to authenticate to the authorization server.
Then the authorization server validates the authorization code and client information, and if valid, responds back with access token and refresh token.

If the request to *authorization endpoint* fails due to missing, invalid, or mismatching redirection uri, or if the client identifier is missing or invalid, the authorization server inform the resource owner of the error and not redirect the user-agent.

However, if the resource owner denies the access request or if the request fails for other reasons, the authorization server informs the client by adding some parameters to response.

### Implicit 

Implicit grant is a simplified *authorization code grant*. 
Simplified because the client receives the access token as a result of the authorization request, without intermediate credentials such as the authorization code. 
The implicit grant type does not include client authentication, and depends on the presence of the resource owner and the registration of the redirection URI. 
Because the access token is encoded into the redirect URI, it may be exposed to the resource owner and other applications residing on the same device.

As with the authentication code, when the authentication is required the client directs the resource owner to an authorization server using the *authorization endpoint*.
The parameters used in this request are the same as those used in the authorization code grant: **response_type**, **client_id**, **redirect_uri**, **scope** and **state**. The only difference is that in this case the response type must be set to **token**.

Then the authorization server then authenticates the resource owner, asks which scopes he grants or denies, and sends a response to the client, adding some parameters: 

* **access_token:** the access token issued by the authorization server. This information is put on a **fragment URI**.
* **token_type:** the type of the token issued.
* **expires_in:** represent the lifetime in seconds of the access token. This parameter it's not mandatory, but it's recommend.
* **scope:** the scopes that was granted to the client. If identical to the scope requested by the client is optional, otherwise is required.
* **state** value informed by the client when requesting it. If the client informed the value, then the authorization server must include this value in response.

The user-agent follows the redirection instructions by making a request to the web-hosted client resource, but not include the fragment with the access token, that is retains locally by the user-agent. The web-hosted client resource returns a webpage capable of accessing the full redirection URI, including the fragment retained by the user-agent. This web page typically has a script, that is executed by the user-agent and extracts the access token and any other information in the fragment.

Implicit grant doesn't have *refresh_token*. When the token expires, it's necessary that the resource owner authenticate with authorization server again.

If the request to *authorization endpoint* fails due to missing, invalid, or mismatching redirection uri, or if the client identifier is missing or invalid, the authorization server inform the resource owner of the error and not redirect the user-agent. 
However, if the resource owner denies the access request or if the request fails for other reasons, the authorization server informs the client by adding some parameters to response.

Implicit grants improve the responsiveness and efficiency of some clients, since it reduces the number of round trips required to obtain an access token. 
However, this convenience should be weighed against the security implications of using implicit grants.

### Resource Owner Password Credentials

Resource Owner Password Credentials can be used directly as an authorization grant to obtain an access token.
It's suitable in cases where the resource owner has a trust relationship with the client. 
It's recommend that this grant type is used only other flows are not viable.

In this grant type, the client must be capable of obtaining the resource owner's credentials. 
These credentials are used for a single request and are exchanged for an access token, but it's not necessary that the client stored this information.

The client requests an access token from the authorization server's *token endpoint*. 
To make this request, the client authenticates itself on the authorization server. 
The following parameters are used:

* **grant_type:** the grant type used. The value must be set to **password**.
* **username:** the resource owner username
* **password:** the resource owner password
* **scope:** scope requested by the client. This parameter is optional and if left blank, the authorization server will use its default scopes.

Then, the authorization server needs to authenticate the client, with his credentials, and if the client information is correct, authenticate the resource owner password credentials. 
If all the information is valid, the authorization server return *access_token*, *refresh_token*, *expires_in* and *token_type*.

### Client Credentials

We may use Client Credentials when protected resources are controlled by the client, or those of another resource owner that have been previously arranged with the authorization server
This grant type has only two steps.

The client authenticates with the authorization server and requests an access token from the *token endpoint*.
The following parameters are used:

* **grant_type:** the grant type used. The value must be set to **client_credentials**.
* **scope:** scope requested by the client. This parameter is optional and if left blank, the authorization server will use its default scopes.

The client still has to authenticate with the authorization server to do this request. 
If everything is correct, the authorization server return *access_token*, *refresh_token*, *expires_in* and *token_type*.


## OAuth 2.0 Login

## OAuth 2.0 Client

## OAuth 2.0 Resource Server

