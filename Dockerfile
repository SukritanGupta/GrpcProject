
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the jar file from the build stage
COPY  bookDetailsService/target/bookDetailsService-0.0.1-SNAPSHOT.jar app.jar

# Expose the ports the application runs on
EXPOSE 8081
EXPOSE 9090

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar","--spring.profiles.active=prod"]