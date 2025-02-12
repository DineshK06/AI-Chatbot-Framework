# Use OpenJDK 23 as base image
FROM openjdk:23-jdk

# Set working directory
WORKDIR /app

# Copy the application JAR file
COPY target/chatbot-1.0.0.jar chatbot.jar

# Pass API Key as an ARG (to avoid storing it inside the image)
ARG OPENAI_API_KEY
ENV OPENAI_API_KEY=${OPENAI_API_KEY}

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "chatbot.jar"]
