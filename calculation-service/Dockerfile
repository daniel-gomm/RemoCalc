FROM openjdk:11

COPY ./target/calculation-service-1.0-SNAPSHOT.jar /calculator/service.jar

WORKDIR /calculator

ENTRYPOINT ["java", "-jar", "service.jar"]