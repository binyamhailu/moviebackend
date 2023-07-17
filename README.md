# Spring Boot Movie Backend Application

This is a Spring Boot application that provides <Description of the application>.

- Basic Crud for a Movie
- Basic Crud for a Movie Screening
- Basic Crud for a User
- Basic RBAC using Spring Boot 3 and  Spring Security 6
- Global Exception Handler
- Unit test using Mockito and Junit

## Prerequisites

To run this application, you need the following software installed on your machine:

- Java Development Kit (JDK) 17 or later
- Docker (for running using Docker Compose)

## Running the Application

### Using Maven

1. Clone this repository to your local machine.
2. Navigate to the project root directory.
3. Build the application using Maven:
   ```bash
   mvn clean package
   ```
4. Run the application:
   ```bash
   java -jar target/moviebapp-backend.jar
   ```

The application will start, and you can access it at `http://localhost:8080`.

### Swagger URL

The Swagger UI URL will be accessed, and you can access it at `http://localhost:8080/swagger-ui.html`.

### Using Docker Compose

If you have Docker installed, you can run the application using Docker Compose:

1. Clone this repository to your local machine.
2. Navigate to the project root directory.
3. Build the Docker image and start the containers using Docker Compose:
   ```bash
   docker-compose up -d
   ```

With Docker Compose, both the Spring Boot application and the PostgreSQL database will be started in separate containers. The application will be accessible at `http://localhost:8080`.


## Configuration

The application can be configured using the `application.yml` file. For the database connection, the following configuration is used:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://database:5432/spring
    username: postgres
    password: changeme
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
    properties:
      hibernate:
        format_sql: true
    database: postgresql
    database-platform: org.hibernate.dialect.PostgreSQLDialect

application:
  security:
    jwt:
      secret-key: 404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
      expiration: 86400000 # a day
      refresh-token:
        expiration: 604800000 # 7 days
    admin:
      password: password
```

## CI Integration
[![Java CI with Maven](https://github.com/mengestu/moviebackend/actions/workflows/maven.yml/badge.svg)](https://github.com/mengestu/moviebackend/actions/workflows/maven.yml)
## Unit Testing

The application's service layer is tested using JUnit and Mockito. The unit tests can be found in the `src/test` directory.
