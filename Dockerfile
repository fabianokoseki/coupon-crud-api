# syntax=docker/dockerfile:1

FROM openjdk:11
COPY build/libs/app.jar app.jar
COPY application.properties application.properties
ENTRYPOINT ["java", "-jar", "-Duser.timezone=UTC", "app.jar"]
