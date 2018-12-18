# REST API Usage Example

See also [auth-server README.md](../auth-server/README.md).

```bash
#REST_API_URL=https://rent-a-car-rest-api.herokuapp.com

REST_API_URL=http://localhost:7082

curl -X POST -H "Authorization: Bearer $TOKEN" -H 'Content-Type: application/json' "$REST_API_URL/locations" -d '{"name": "SBZ"}' -i

curl -X POST -H "Authorization: Bearer $TOKEN" -H 'Content-Type: application/json' "$REST_API_URL/locations" -d '{"name": "OTP", "address": "Calea Bucureştilor 224E, Otopeni 075150"}' -i

curl -X POST -H "Authorization: Bearer $TOKEN" -H 'Content-Type: application/json' "$REST_API_URL/locations" -d '{"name": "MUC", "address": "Nordallee 25, 85356 München, Germany"}' -i

curl -X POST -H "Authorization: Bearer $TOKEN" -H 'Content-Type: application/json' "$REST_API_URL/vehicles" -d '{"id": "SB-01-AAA", "type": "Economy Car", "brand": "Dacia", "model": "Logan", "rate": 22.91, "location": "SBZ"}' -i

curl -X POST -H "Authorization: Bearer $TOKEN" -H 'Content-Type: application/json' "$REST_API_URL/vehicles" -d '{"id": "SB-02-AAA", "type": "Economy Car", "brand": "Opel", "model": "Astra", "rate": 24.72, "location": "SBZ"}' -i

curl -X POST -H "Authorization: Bearer $TOKEN" -H 'Content-Type: application/json' "$REST_API_URL/vehicles" -d '{"id": "SB-03-AAA", "type": "Economy Car", "brand": "Dacia", "model": "Logan", "rate": 22.91, "location": "SBZ"}' -i

curl -X POST -H "Authorization: Bearer $TOKEN" -H 'Content-Type: application/json' "$REST_API_URL/vehicles" -d '{"id": "SB-04-AAA", "type": "Economy Car", "brand": "Audi", "model": "3000 msi", "rate": 149, "location": "SBZ"}' -i

curl -H "Authorization: Bearer $TOKEN" "$REST_API_URL/vehicles?pickupDate=2018-10-01T10:00:00.000Z&pickupLocation=SBZ&returnDate=2018-10-03T10:00:00.000Z&returnLocation=SBZ" -i

curl -H "Authorization: Bearer $TOKEN" "$REST_API_URL/locations" -i
```
