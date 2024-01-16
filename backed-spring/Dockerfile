# Use an official OpenJDK runtime as a base image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR file into the container at /app/app.jar
COPY /target/*.jar /app/app.jar

# Expose the application port
EXPOSE 9000

# Specify the command to run on container startup
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
