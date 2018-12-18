OAUTH2_SERVER_URL=http://localhost:7081

RESOURCE_SERVER_URL=http://localhost:7082

CLIENT_ID=account

CLIENT_SECRET=password

BASE64_AUTH=\`echo -n "$CLIENT_ID:$CLIENT_SECRET" | openssl base64\`

How to access
---

`curl -X POST -H "Authorization: Basic $BASE64_AUTH" "$OAUTH2_SERVER_URL/oauth/token?grant_type=client_credentials"`

TOKEN=1c85c36a-3d85-4f99-8f63-12a000adddca

`curl -H "Authorization: Bearer $TOKEN" "$RESOURCE_SERVER_URL/person"`
