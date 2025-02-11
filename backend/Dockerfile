# Use OpenJDK 23 as base image
FROM openjdk:23-jdk

# Set working directory
WORKDIR /app

# Copy the application JAR file
COPY target/chatbot-1.0.0.jar chatbot.jar

COPY .env .env

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "chatbot.jar"]
