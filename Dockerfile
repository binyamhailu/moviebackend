# Use a base image with Java and appropriate JRE version
FROM openjdk:17-jre-slim
WORKDIR /app
COPY target/your-spring-boot-app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
