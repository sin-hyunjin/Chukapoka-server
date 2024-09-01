FROM openjdk:11
WORKDIR /app
COPY build/libs/server-0.0.1-SNAPSHOT.jar server.jar
CMD ["java", "-jar", "server.jar"]
