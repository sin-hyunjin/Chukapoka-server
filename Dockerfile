# Stage 1: Build the application using Gradle
FROM gradle:7.3.0-jdk17 AS build
RUN apk add --no-cache bash
# Set the working directory
WORKDIR /app

# Copy necessary files for the build
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
COPY src ./src

RUN ./gradlew dependencies --no-daemon

COPY . .

RUN chmod +x ./gradlew

# Build the application
RUN ./gradlew clean build --no-daemon -x test

# Stage 2: Create the final image
FROM bellsoft/liberica-openjdk-alpine:17


# Copy the jar file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
