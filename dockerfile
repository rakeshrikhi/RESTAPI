FROM maven:3.5.4-jdk-8-alpine as maven
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn dependency:go-offline -B
RUN mvn package
FROM openjdk:8u171-jre-alpine
WORKDIR /RESTAPI
COPY --from=maven target/client-rest-0.0.1-SNAPSHOT-jar-with-dependencies.jar ./client-rest-0.0.1-SNAPSHOT-jar-with-dependencies.jar
CMD ["java", "-jar", "./client-rest-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]