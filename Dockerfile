# Use the official OpenJDK 8 base image
FROM openjdk:8-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the build artifact from the host to the container
COPY build/libs/final-project-0.0.1-SNAPSHOT.jar /app/final-project-0.0.1-SNAPSHOT.jar

# Expose port 8080 to the outside world
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "final-project-0.0.1-SNAPSHOT.jar"]
