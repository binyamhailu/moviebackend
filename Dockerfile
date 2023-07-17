# Use a base image with Java and appropriate JRE version
FROM openjdk:17-jre-slim
WORKDIR /app
COPY target/moviebapp-backend.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
