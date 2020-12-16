# Private Data Extractor Backend Application

### Prerequisites
* **JDK >= 1.8.0_x**;
* **Apache Maven >= 3.6.1**;
* **Docker >= 19.03.13**;
* **docker-compose >= 1.27.4**;

## Build
- Clone project
```
git clone <REMOTE REPOSITORY>
```
- Run command
```
mvn clean install 
```
 
## Run
- Execute .jar file (in *./target* folder)
```
java -jar target/pd-extractor-0.0.1-SNAPSHOT.jar
```

## Configuration
Application config and services' endpoints are specified in: `/src/main/resources/application.properties`
 
### Ports exposed by component
Backend application port: 8079
 
### Service Endpoints
REST API base path: /api
