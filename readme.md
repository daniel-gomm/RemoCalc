# RemoCalc - A remote Calculator with CLI Client Application

## General Information
This remote calculator provides a calculator accessible as a SOAP web service. The web-service is referred to as
calculation-service. It is accompanied by a cli-client that can be used to interact with the calculation-service.
To get started, you have to first build the project, then deploy the web-service before you can use the cli-client.
### Capabilities
The calculator can handle calculations involving the following mathematical operators: +, -, *, /\
Decimal numbers are expressed with a dot.\
It is possible to chain an arbitrary number of calculations, e.g.: 500+3*-29+400/-3*5+6000

## How to build the project
The project is build via maven. To build the server-side project run the following command from the projects
[root directory](.):
```shell
mvn -pl '!cli-client' clean install
```
(on Windows you might have to use " instead of ')

The cli-client can only be build once the calculation-service is running since it creates the sources for the SOAP endpoint
during the maven build process, which necessitates that the service is running at that time.

The cli-client is build by running the following command from the [cli-client directory](./cli-client), while the 
calculation-service is running:
```shell
mvn clean install
```

## Deploying the calculation-service
The calculation service can be deployed either directly on the host operating system, or via a provided Docker Container.
### Deploying directly on the OS
Run the following command from the projects [root directory](.):
```shell
java -jar calculation-service/target/calculation-service-1.0-SNAPSHOT.jar
```

### Deploying as a Docker Container
To deploy the service as a docker container, the container image has to be build first by running the following command
from the projects [root directory](.):
```shell
docker build -t example/calculation-service:1.0.0 ./calculation-service/.
```

To run the container execute the following command:
```shell
docker run -p 8080:8080 example/calculation-service:1.0.0
```

## Running the cli-client
After building the cli-client module you have two options to use the cli-client application. Either you run it with the calculation
you want to solve as argument, or you run it without arguments to enter a session where you can directly enter and solve
as many successive calculations as you want.\
To run the client with the calculation as argument, run the following from the projects [root directory](.) (modify the
calculation to fit whatever calculation you want to solve):
```shell
java -jar cli-client/target/cli-client-1.0-SNAPSHOT.jar "500+3*-29+400/-3*5+6000"
```
To enter a session run the application without arguments:
```shell
java -jar cli-client/target/cli-client-1.0-SNAPSHOT.jar
```