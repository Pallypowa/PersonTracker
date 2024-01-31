A szükséges MSSQL adatbázist egy docker container futtatásával hoztam létre. 

Először az image leszedésével: 

```docker pull mcr.microsoft.com/mssql/server:2022-latest```

Majd annak futtatásával: 

```docker run -d --name sql_server_test -e 'ACCEPT_EULA=Y' -e 'SA_PASSWORD=strongPassword123' -p 1433:1433 mcr.microsoft.com/mssql/server:2022-latest```

Utána az adatbázisban létrehoztam a Persons táblát: 

```sql
CREATE DATABASE Persons
```
A táblákat a Hibernate automatikusan létrehozza, majd droppolja is őket az app leállítása előtt. (spring.jpa.hibernate.ddl-auto=create-drop config)

Teszteket a service layerekre írtam. 
