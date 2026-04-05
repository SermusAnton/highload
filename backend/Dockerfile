# Stage 1: Build the application
FROM gradle:jdk17-corretto AS build

# Set the working directory inside the build container
WORKDIR /app

# Copy the Gradle wrapper, build scripts, and settings
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Copy the source code
COPY backend/build.gradle.kts backend/
COPY backend/src backend/src

# Make the Gradle wrapper executable
RUN chmod +x gradlew

# Build the application (e.g., generate a JAR or WAR file)
RUN ./gradlew :backend:bootJar --no-daemon

# Stage 2: Create the runtime image
FROM openjdk:17.0.2-slim-buster

# Set the working directory inside the runtime container
WORKDIR /app

# Copy the built artifact from the build stage
COPY --from=build /app/backend/build/libs/*.jar app.jar

# Expose the port your application listens on (if applicable)
EXPOSE 8080

# Define the command to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]