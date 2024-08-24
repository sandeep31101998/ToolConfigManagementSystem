# Use the official OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the local machine to the container
COPY target/ToolConfiguration-0.0.1-SNAPSHOT.jar /app/ToolConfiguration-0.0.1-SNAPSHOT.jar

# Expose the port that your application runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "ToolConfiguration-0.0.1-SNAPSHOT.jar"]


