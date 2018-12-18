```bash
SERVER_URL=http://localhost:7081

CLIENT_URL=http://localhost

CLIENT_ID=rent-a-car-client-id

CLIENT_SECRET=rent-a-car-client-secret

BASE64_AUTH=`echo -n "$CLIENT_ID:$CLIENT_SECRET" | openssl base64`
```

Client Credentials
---

`curl -X POST -H "Authorization: Basic $BASE64_AUTH" "$SERVER_URL/oauth/token?grant_type=client_credentials"`

The following works only with security.allowFormAuthenticationForClients(); (security is of type AuthorizationServerSecurityConfigurer).

`curl -X POST "$SERVER_URL/oauth/token?grant_type=client_credentials&client_id=$CLIENT_ID&client_secret=$CLIENT_SECRET"`

Password
---

WebSecurityConfigurer defines a user name and password. Otherwise, Spring Security defines the default user name `user` and logs a random generated password.
Using generated security password: 691ee09d-4be3-4b22-abba-d15871b6b1da

USER_NAME=user

USER_PASSWORD=user

`curl -X POST -H "Authorization: Basic $BASE64_AUTH" "$SERVER_URL/oauth/token?grant_type=password&username=$USER_NAME&password=$USER_PASSWORD"`

The following works only with security.allowFormAuthenticationForClients(); (security is of type AuthorizationServerSecurityConfigurer).

`curl -X POST "$SERVER_URL/oauth/token?grant_type=password&username=$USER_NAME&password=$USER_PASSWORD&client_id=$CLIENT_ID&client_secret=$CLIENT_SECRET"`

Authorization Code
---

From a web browser access

$SERVER_URL/oauth/authorize?response_type=code&client_id=$CLIENT_ID&client_secret=$CLIENT_SECRET&redirect_uri=$CLIENT_URL

You must authenticate as user and authorize the $CLIENT_ID application.
Then you will get redirected to an URL like $CLIENT_URL/?code=iFGbWT

CODE=iFGbWT

`curl -X POST -H "Authorization: Basic $BASE64_AUTH" "$SERVER_URL/oauth/token?grant_type=authorization_code&code=$CODE&redirect_uri=$CLIENT_URL"`

The following works only with security.allowFormAuthenticationForClients(); (security is of type AuthorizationServerSecurityConfigurer).

`curl -X POST "$SERVER_URL/oauth/token?grant_type=authorization_code&client_id=$CLIENT_ID&client_secret=$CLIENT_SECRET&code=$CODE&redirect_uri=$CLIENT_URL"`

Implicit
---

From a web browser access

"$SERVER_URL/oauth/authorize?response_type=token&client_id=$CLIENT_ID&redirect_uri=$CLIENT_URL"

You will be redirected to an URL with the access token in it. For example: $CLIENT_URL/#access_token=6e47d1bb-65df-4541-8ddc-0459a6e47533&token_type=bearer&expires_in=43058&scope=read%20write

Note that this type of OAuth2 grant does not require the Client Secret and no user login.

Check access token
---

TOKEN=1c85c36a-3d85-4f99-8f63-12a000adddca

`curl -H "Authorization: Basic $BASE64_AUTH" "$SERVER_URL/oauth/check_token?token=$TOKEN"`

References
---

OAuth2 simplified, https://aaronparecki.com/oauth-2-simplified