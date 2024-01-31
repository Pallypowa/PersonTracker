### Setup

A szükséges MSSQL adatbázist egy docker container futtatásával hoztam létre.

Először az image leszedésével: 

```docker pull mcr.microsoft.com/mssql/server:2022-latest```

Majd annak futtatásával: 

```docker run -d --name sql_server_test -e 'ACCEPT_EULA=Y' -e 'SA_PASSWORD=strongPassword123' -p 1433:1433 mcr.microsoft.com/mssql/server:2022-latest```

Utána az adatbázisban létrehoztam a Persons táblát: 

```sql
CREATE DATABASE Persons
```
A táblákat a Hibernate automatikusan létrehozza, majd droppolja is őket az app leállítása előtt, amiről az alábbi config gondoskodik az application-dev.properties fileban:

```spring.jpa.hibernate.ddl-auto=create-drop```

### Endpoints

A service a default 8080 porton fut. Az alábbi endpointokat hoztam létre: 

#### Person

| Metódus |       Végpont       |                   Paraméterek 
|---------|:-------------------:|------------------------------:
| GET     | /api/persons                 | - 
| GET     | /api/persons/{id}            | id: UUID (path variable) 
| GET     | /api/persons/{id}/addresses  | id: UUID 
| GET     | /api/persons/{id}/contacts   | id: UUID 
| POST    | /api/persons                 | body: PartnerDTO
| PUT     | /api/persons                 | body: PartnerDTO
| DELETE  | /api/persons/{id}            | id: UUID

#### Address
| Metódus |       Végpont       |                   Paraméterek 
|---------|:-------------------:|------------------------------:
| GET     | /api/addresses                 | - 
| GET     | /api/addresses/{id}            | id: UUID (path variable) 
| POST    | /api/addresses                 | body: AddressDTO
| PUT     | /api/addresses                 | body: AddressDTO
| DELETE  | /api/addresses/{id}            | id: UUID

#### Contact
| Metódus |       Végpont       |                   Paraméterek 
|---------|:-------------------:|------------------------------:
| GET     | /api/contacts                 | - 
| GET     | /api/contacts/{id}            | id: UUID (path variable) 
| POST    | /api/contacts                 | body: ContactDTO
| PUT     | /api/contacts                 | body: ContactDTO
| DELETE  | /api/contacts/{id}            | id: UUID

Teszteket a service layerekre írtam. 
