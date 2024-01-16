# Use an official OpenJDK runtime as a base image
FROM adoptopenjdk/openjdk17:jdk-alpine

WORKDIR /app

COPY target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
