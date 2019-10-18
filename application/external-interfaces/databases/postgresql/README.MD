The database can be created manually with this script.

Notice that the default schema is `auth`. This is where Spring will keep users and roles.

```
DROP DATABASE IF EXISTS rent_a_car;

CREATE DATABASE rent_a_car;

ALTER database "rent_a_car" SET search_path TO auth;
```