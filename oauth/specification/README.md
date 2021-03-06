# OAuth and OpenID Connect

## OAuth Framework

OAuth 2.0 is a framework that allows a user give temporary access to a third party application to their information, giving more flexibility and improve security, as the user doesn't need to share his credentials with anyone. The user also can limit the third party access, either by time or the information they can access.

### Roles

OAuth define four roles: 

* **resource owner:** entity capable of grating access to the protected resource.
* **resource server:** server hosting the protected resource
* **client:** application making protected resource requests, on behalf of the resource owner and with his authorization
* **authorization server:** server issuing access tokens to the client after successfully authenticating the resource owner and obtaining authorization.

This four agents interact with each other to ensure the best security for the protected resource. 

The resource server must trust the authorization server, and sometimes these two entities are represented by the same application. The authorization server may issue access tokens accepted by more than one resource server.

#### Client registration

In most cases, clients need to register with the authorization server. This registration can be done in different ways, such as by direct interaction or by authorization server discovery client on a trusted channel. 

When registering, clients need to specify a type, based on their ability to maintain the confidentially of their client credentials and the authorization server's definitions. There are two types of clients: **confidential** and **public**.

After registering, clients will have a unique identifier, which is used by the authorization server to identify the client. With this identifier, know as **client_id**, clients must authenticate themselves in authorization server, when it's required. This authentication can be done through HTTP basic authentication or another similar mechanism. In addition to *client_id*, clients need a way to prove their identity, and this can be done by **client_secret**, when a password was given to the client, or by **redirect_uri**, which is an URI informed by the client during registration and can be validated when a request is made.

### Protocol Endpoints

The authorization process utilizes two authorization server endpoints and one client endpoint. Not all types of authorization grants use both endpoints.

#### Authorization Endpoint 

This is a server endpoint that can be used by the client to obtain authorization from the resource owner via user-agent redirection. Then, the resource owner is redirected to the authorization server, that is responsible to verifying the identity of the resource owner. The parameter *response_type* can be used to inform the authorization server the desired grant type. This parameter could assume two values: 

* **code**: used in the authorization code flow, the endpoint will return a code that can be changed for an access token in another step
* **token**: used in the implicit flow, the endpoint will return an access token direct to the client, after authentication of the resource owner.

#### Token Endpoint

The token endpoint is other server endpoint, used by the client to obtain an access token presenting its authorization grant or refresh token. The client need authentication to call this endpoint, so he needs to inform his credentials. 

#### Redirection Endpoint

This is a client endpoint defined by Oauth, used by the authorization server to return responses containing authorization credentials to the client through the resource owner's user-agent, after completing its interactive with the resource owner. This endpoint need to be registered by the client during its registration in the authorization server.

### Scopes

When the client requests authorization for the resource owner through the authorization server, he needs to specify the scopes that he wants to access. Then the authorization server needs to ask the resource owner which scopes he allows the client access. The resource owner can grant access to all scopes requested by the client, some of them or none.

These scopes are used to give right of access to protected resources. For example, the resource owner might give a client access to their information such as name and email, but refuse access to information such as phone and address.

It's important that the resource server knows about these scopes, and is able to expose to the client only what has been released by the resource owner.

### Access token and Refresh token

OAuth works with two types of token. 

**Access token** is token used by the client to access protected resource. This token must be sent to resource server whenever a request for a protected resource is made. Access token is a short live token, which means that it expires after a short time. This time is set by the authorization server, but it's usually about 1 hour. 

**Refresh token** is a token used by the client to get new access token without the resource owner authenticating again. This token is typically long-lasting credentials, which means that it's valid to a long period. When the client knows that the access token will expire, or when a request return an error because that, the client can request a new token for the authorization server.
For this, he makes request to *token endpoint*, with two parameters:

* **grant_type:** type of the request, in this case the value must be set to **refresh_token**.
* **refresh_token:** a valid refresh token to authenticate in the authorization server .

The client also needs to send their credentials to validate in the authorization server. The authorization server will validate the client credentials, and the refresh token.
If everything is ok, the authorization server will return a new *access token* and sometimes a new *refresh token*. If a new refresh token is returned, the old refresh token (used in the request) has become invalid.

### Grant Types

The authorization from resource owner to a client is expressed in the form of an authorization grant, which is a credential representing the resource owner's authorization and allow the client to request an access token. OAuth defines four grant types: authorization code, implici flow, resource owner password credentials, and client credentials.

#### Authorization Code

Authorization Code Grant is a redirection-based flow, so the client must be able to interact with resource owner's user-agent. In this flow, the resource owner's credentials are never shared with the client.

When authentication is required, the client directs the resource owner to an authorization server using the *authorization endpoint*, including some information: 

* **response type:** in authorization code the value must be set to **code**.
* **client_id:** client identifier in the authorization server
* **redirect_uri:** uri to redirect the resource owner after the authentication. This parameter is optional and if left blank, the authorization server tries to use the **redirect_uri** informed by the client during its registration.
* **scope:** scope requested by the client. This parameter is optional and if left blank, the authorization server will use its default scopes.
* **state:** a value used by the client to maintain state between the request and the callback. This will be included by authorization server when redirecting the user-agent back to the client. It's not mandatory, but should be used for security reasons.

When receives the request, the authorization server authenticates the resource owner and obtains its authorization for scopes requested by the client. Assuming that the resource owner can authenticate and grant access, the authorization server directs the resource owner back to the client using the *redirect_uri*. Again, some information is included:

* **code:** authorization code generated by the authorization server.
* **state** value informed by the client when requesting it. If the client informed the value, then the authorization server must include this in response.

So, the client receives the response and makes another request to the *token endpoint*. Now the user is not redirecting, because this process occurs in background. To make a request to token endpoint, we use some parameters: 

* **grant_type:** in authorization code the value must be set to **authorization_code**.
* **code:** the authorization code received from the authorization server.
* **redirect_uri:** if redirect_uri was included in the authorization request, this value is mandatory and must be identical to the value used previously.

When making this request, the client need to authenticate to the authorization server. Then the authorization server validates the authorization code and client information, and if valid, responds back with access token and refresh token.

If the request to *authorization endpoint* fails due to missing, invalid, or mismatching redirection uri, or if the client identifier is missing or invalid, the authorization server inform the resource owner of the error and not redirect the user-agent. However, if the resource owner denies the access request or if the request fails for other reasons, the authorization server informs the client by adding some parameters to response.

#### Implicit 

Implicit grant is a simplified *authorization code grant*. Simplified because the client receives the access token as a result of the authorization request, without intermediate credentials such as the authorization code. The implicit grant type does not include client authentication, and depends on the presence of the resource owner and the registration of the redirection URI. 
Because the access token is encoded into the redirect URI, it may be exposed to the resource owner and other applications residing on the same device.

As with the authentication code, when the authentication is required the client directs the resource owner to an authorization server using the *authorization endpoint*. The parameters used in this request are the same as those used in the authorization code grant: **response_type**, **client_id**, **redirect_uri**, **scope** and **state**. The only difference is that in this case the *response_type* must be set to **token**.

Then the authorization server authenticates the resource owner, asks which scopes he grants or denies, and sends a response to the client, adding some parameters: 

* **access_token:** the access token issued by the authorization server. This information is put on a **fragment URI**.
* **token_type:** the type of the token issued.
* **expires_in:** represent the lifetime in seconds of the access token. This parameter it's not mandatory, but it's recommend.
* **scope:** the scopes that was granted to the client. If identical to the scope requested by the client is optional, otherwise is required.
* **state** value informed by the client when requesting it. If the client informed the value, then the authorization server must include this value in response.

The user-agent follows the redirection instructions by making a request to the web-hosted client resource, but not include the fragment with the access token, that is retains locally by the user-agent. The web-hosted client resource returns a webpage capable of accessing the full redirection URI, including the fragment retained by the user-agent. This web page typically has a script, that is executed by the user-agent and extracts the access token and any other information in the fragment.

Implicit grant doesn't have *refresh_token*. When the token expires, it's necessary that the resource owner authenticate with authorization server again.

If the request to *authorization endpoint* fails due to missing, invalid, or mismatching redirection uri, or if the client identifier is missing or invalid, the authorization server inform the resource owner of the error and not redirect the user-agent. However, if the resource owner denies the access request or if the request fails for other reasons, the authorization server informs the client by adding some parameters to response.

Implicit grants improve the responsiveness and efficiency of some clients, since it reduces the number of round trips required to obtain an access token. However, this convenience should be weighed against the security implications of using implicit grants.

#### Resource Owner Password Credentials

Resource Owner Password Credentials can be used directly as an authorization grant to obtain an access token. It's suitable in cases where the resource owner has a trust relationship with the client. It's recommend that this grant type is used only other flows are not viable.

In this grant type, the client must be capable of obtaining the resource owner's credentials. These credentials are used for a single request and are exchanged for an access token, but it's not necessary that the client stored this information.

The client requests an access token from the authorization server's *token endpoint*. To make this request, the client authenticates itself on the authorization server. The following parameters are used:

* **grant_type:** the grant type used. The value must be set to **password**.
* **username:** the resource owner username
* **password:** the resource owner password
* **scope:** scope requested by the client. This parameter is optional and if left blank, the authorization server will use its default scopes.

Then, the authorization server needs to authenticate the client, with his credentials, and if the client information is correct, authenticate the resource owner password credentials. 
If all the information is valid, the authorization server return *access_token*, *refresh_token*, *expires_in* and *token_type*.

#### Client Credentials

We may use Client Credentials when protected resources are controlled by the client, or those of another resource owner that have been previously arranged with the authorization server
This grant type has only two steps.

The client authenticates with the authorization server and requests an access token from the *token endpoint*. The following parameters are used:

* **grant_type:** the grant type used. The value must be set to **client_credentials**.
* **scope:** scope requested by the client. This parameter is optional and if left blank, the authorization server will use its default scopes.

The client still has to authenticate with the authorization server to do this request. If everything is correct, the authorization server return *access_token*, *refresh_token*, *expires_in* and *token_type*.

## OpenID Connect

OpenID Connect (OIDC) is a simple identity layer on top of the OAuth 2.0 framework. OAuth doesn't provide a way for the client to validate the identity of the end user, after the user authenticates in authorization server. OIDC enables this functionality and provides ways to obtain basic profile information about end user. To use OIDC extension, the client need to include the **openid** scope in the _Authorization Request_, on a server that supports OpenID Connect. 

The steps used by OIDC are the same as those used by OAuth 2.0 and may vary depending on the grant type that is used. Almost always, the only change is that the identity of end user is returned along with the access token.

### ID Token

ID Token is the primary extension that the OIDC makes to OAuth. It's a security token that contains **Claims** about the authentication of an end user by an authorization server.

*Claims* are name/value pairs that contain information about a user, as well meta-information about the OIDC service. The OIDC has defined some claims that are used within OAuth flows used by OpenID Connect. However, it's possible to using any valid name/value pair.

### Authentication

OpenID Connect performs authentication to log in the End-User or to determine that the End-User is already logged in. The result of authentication return to the client securely, in an ID Token. 
This token contains some Claims, that expressing such information as the Issuer, the Subject Identifier, when the authentication expires, etc.

Authentication can take one of three paths: the **Authorization Code Flow**, the **Implicit Flow**, or the **Hybrid Flow**. The parameter **response_type** should be used to determine the authorization processing flow.

* _response_type=code_ para Authorization Code Flow
* _response_type=id_token_ para Implicit Flow
* _response_type=id_token token_ para Implicit Flow
* _response_type=code id_token_ para Hybrid Flow
* _response_type=code token_ para Hybrid Flow
* _response_type=code id_token token_ para Hybrid Flow

#### Authorization Code Flow

When we use Authorization Code Flow, all tokens are returned from the Token Endpoint. In OIDC, this flow work in much the same way as defined in the OAuth specification, but now the cliente exchange the authorization code for an ID Token and an Access Token.

In this flow, there is only one change from OAuth. In OAuth, when a request is sent to the authorization server, the **scope** parameter is not required. When this happens, the Authorization Server will use its default scopes. When using OIDC we need to send the scope value **openid**, to indicate to the authorization server, that we want to use OIDC. If this scope is not provided by the client, the authorization server assumes that OIDC is not used.

Parameters **response_type**, **client_id**, **redirect_uri**, **state** work the same way. The OIDC also defines other optional parameters: 

* **nonce:** used to associate a client session with an ID Token. The value is passed through unmodified from the Authentication Request to the ID Token
* **display:** specifies how the Authorization Server displays the authentication and consent user interface pages to the end user. The defined values are: _page_, _popup_, _touch_ and _wap_ 
* **prompt:** specifies whether the Authorization Server prompts the end user for reauthentication and consent. The defined values are: _none_, _login_, _consent_ and *select_account*
* **max_age:** used to specify the time in seconds since the last time the end user was actively authenticated by the OP.
* **ui_locales:** end-user preferred languages and scripts for the user interface.
* **id_token_hint:** used to pass the id_token previously issued by the authorization server.  If the End-User identified by the ID Token is logged in or is logged in by the request, then the Authorization Server returns a positive response; otherwise, it SHOULD return an error, such as *login_required*.
* **login_hint:** hint to the authorization server about the login identifier the end user might use to log in.
* **acr_values:** requested Authentication Context Class Reference values

After all the flow, when receiving the tokens from authorization server, the client must validate the ID Token, to determine that if the end user authenticated by the authorization server is the same as it expected, and if the token was generated for him. This is the real improvement provided by the OIDC. If the validation fails, the client must discard these tokens and not allow the user access. When using the authorization code flow, the ID token has the addition claim **at_hash**, that is the access token hash value.

#### Implicit Flow

With Implicit Flow, all tokens are returned from Authorization Endpoint. The Token Endpoint is not used, as is not necessary to exchange any code for a token. This flow is mainly used by Clients implemented in a browser using a scripting language, as suggested by OAuth.

Implicit flow authorization request in OIDC works in much the same way as the Authorization Code Flow, with two important differences. The first one, the _response_type_ can receive two values: **id_token token** and **id_token**. When passing only the value *id_token*, the access_token is not returned. When passing *id_token token*, both ID and access token are returned. The second difference is the **nonce** parameter. This parameter is optional in Authorization Code Flow, but it is required in Implicit Flow, so it's mandatory to pass a value to associate a client session with an ID Token.

It's important to note that the tokens are returned in a fragment in the response URL, as is defined by OAuth.

Once the client receives the response from authorization server, as in authorization code, it's necessary validate the information. The returned contains the same values that is returned by the token endpoint in authorization code, excluding the *refresh_token*: **access_token**, **refresh_token**, **id_token** and **expires_in**.

Likewise, the client must validate the ID token, as happens in authorization code, to ensure that the authenticated user is the expected user, and has authenticated for that customer. In the ID Token, the *nonce* parameter is returned from authorization server, and must be the same passed from the client. The *at_hash* parameter is also returned.

#### Hybrid Flow

Hybrid flow, as the name suggests, is a mix between Authorization Code and Implicit Flow. The mechanism for authenticating and getting the end user authorization is the same, but some tokens are returned from the authorization endpoint and others are returned from the token endpoint.

To indicate to the authorization server which token should be returned from authorization endpoint, we use the *response_type* parameter. This parameter can receive three combinations of values: **code id_token**, **code token** and **code id_token token**. 

* The **access token** and **token type** are returned when *code token* or *code id_token token* is used. 
* The **id_token** is returned when *code id_token* or *code id_token token* is used.
* The parameter *code* is always returned, no matter which *response_type* is used.

As with Implicit Flow, tokens will be returned for the client in a fragment URL.

When using in *response_type* a value that not returned an access token, we must use the token endpoint to exchange the authorization code to a valid access token. This request work the same manner as for the Authorization Code Flow. 

In Hybrid Flow, the ID Token returned from authorization endpoint and token endpoint are the same. The content of this token has three additional parameter. As in the Implicit Flow, the parameter *nonce* is required when we request the authorization, so it is always returned in the ID Token. The parameter *at hash* also returned, as in the other two flows.
A third parameter is returned in Hybrid Flow, called **c_hash**, that contained a *code* hash value. This hash it's mandatory when the ID Token is returned from authorization endpoint with a *code*.

The Authorization Server may choose to returned fewer claims about end user from the authorization endpoint, for privacy reasons. Likewise, the *at_hash* and *c_hash* parameter may be omitted from the ID token returned from the token endpoint, even when these claims are present in the ID Token returned from authorization endpoint. This is because ID Token and Access Token values returned from the Token Endpoint are already cryptographically bound together by the TLS encryption performed by the Token Endpoint.

### Claims and User Endpoint

Claims are the information about end user. Much information about the user can be retrieved with claims, from the preferred username to user locale. Other Claims may be used in conjunction with the standard Claims defined by OIDC. OIDC defined three representations of Claim Values:

* **Normal Claims:** claims that are directly asserted by the OpenID Provider. This claims must be support by OIDC Provider.
* **Aggregated Claims:** claims that are asserted by a Claims Provider other than the OpenID Provider but are returned by OpenID Provider. This type of claims are optional.
* **Distributed Claims:** claims that are asserted by a Claims Provider other than the OpenID Provider but are returned as references by the OpenID Provider. This type of claims are optional too.

Predefined sets of Claims can be requested using specific scope values or individual Claims can be requested using a specific parameter. OIDC defines four scope values that are used to request Claims:

* **profile:** request access to end user's default profile claims, which are: *name*, *family_name*, *given_name*, *middle_name*, *nickname*, *preferred_username*, *profile*, *picture*, *website*, *gender*, *birthdate*, *zoneinfo*, *locale*, and *updated_at*.
* **email:** request access to the *email* and *email_verified*
* **address:** request access to the address claim
* **phone:** request access to the *phone_number* and *phone_number_verified*

These scopes can be requested during the authentication process, passing these values in the scope parameter, together with the *openid* value. Likewise, Authorization Request has a parameter called **claims**, that is used to request a specific individual claim. This parameter value is represented as UTF-8 encoded JSON, and have two top-level members: **userinfo** and **id_token**.
Within these members, we indicate which claims we want the Authorization Server returned, as shown below: 

```
{
   "userinfo":
    {
     "given_name": {"essential": true},
     "nickname": null,
     "email": {"essential": true},
    },
   "id_token":
    {
     "auth_time": {"essential": true},
     "acr": {"values": ["urn:mace:incommon:iap:silver"] }
    }
  }
```

In this JSON, we have some options that indicates how these claims should be returned in individual request. When using **null** value, we indicate that this claim is being requested in the default manner. The **essential** option say whether the claim being requested is an Essential Claim, that indicates to the end user that releasing these claims will ensure a smooth authorization for the specific task requested. The options **value** and **values** indicates that the claim should return with a specific value, or a set of values.

All the user claims are returned with ID Token, after end user authentication. However, there is another option to retrieve user information. The **UserInfo Endpoint**, that is an OAuth protected resource also returns claims about the authenticated end user. To obtain the requested claims about the end user, the client makes a request to the UserInfo Endpoint using a valid Access Token, obtained by authentication. Then, the information are returned in a JSON format, such as happen in ID Token

### Using Refresh Tokens

OAuth 2.0 defines the use of Refresh Token. A request can be made to the Token Endpoint, with the *grant type* value **refresh_token**, to obtain a new valid access token.
The OIDC supports the use of a Refresh Token. 

Upon successful validation of the Refresh Token, the response body is a JSON similar that returned when the client changes an authorization code to an access token.
The only thing different is that this JSON might not contain an *id_token*.
If an ID Token is returned as a result of a token refresh request, it must be compatible with the old ID Token, except the **aud** claim.