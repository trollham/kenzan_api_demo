# Kenzan Employee API test

## Getting Started

### Docker Compose
A preconfigured PostgreSQL database is available by running the service through docker-compose.
```bash
$ docker-compose -f .devcontainer/docker-compose.yml up -d
$ docker exec -it devcontainer_employee-api_1 /bin/bash
# Once in the container, run
$ cd workspace
$ ./mvnw spring-boot:run
```
### To run the API natively
```bash
DATABASE_URL="postgresql://<postgres host>:<postgres port>/<postgres database>" ./mvnw spring-boot:run
```
Replace `<postgres host>`, `<postgres port>`, and `<postgres database>` with the address, port, and database of the Postgres instance the API should connect to. For example:
```bash
DATABASE_URL="postgresql://localhost:5432/employee-api" ./mvnw spring-boot:run
```
By default, the API tries to authenticate with the username `postgres` and the password `password`.
To change these defaults, change these values in `src/main/resources/application.properties`.
```
spring.datasource.username=postgres
spring.datasource.password=password
```
### Using the API
The API supports CRUD operations on the `/employees` endpoint. 
#### Create
```bash
curl -X POST -H 'Content-Type: application/json' localhost:8080/employees \
  -d '{"firstName":"Foo","middleInitial":"Z","lastName":"Bar","dateOfBirth":"1970-01-01","dateOfEmployment":"2022-01-01","status":"ACTIVE"}'
```
### Read
All active employees can be retrieved with the `/employees` endpoint. For example: 
```bash
curl localhost:8080/employees
```
You can also fetch an individual active employee with `/employees/{id}`: 
```bash
curl localhost:8080/employees/1
```
### Update
```bash
curl -X PATCH -H 'Content-Type: application/json' localhost:8080/employees/1 \
  -d '{"status": "ACTIVE"}'
```
### Delete
The DELETE method is protected by HTTP Basic authentication. Running without an authorized user will result in 401 response. To avoid this, run with the user `admin` and the password `admin`.
```bash
curl -X DELETE --user admin:admin localhost:8080/employees/1
```
Deleted employees are marked as inactive and their data is still available in the database, but won't be returned by the API.
