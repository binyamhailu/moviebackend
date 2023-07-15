package com.example.moviebappbackend;

import com.example.moviebappbackend.auth.AuthenticationService;
import com.example.moviebappbackend.auth.RegisterRequest;
import com.example.moviebappbackend.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.example.moviebappbackend.user.Role.ADMIN;
import static com.example.moviebappbackend.user.Role.USER;

@SpringBootApplication
public class MovieAppBackendApplication {
    private static final Logger logger = LoggerFactory.getLogger(MovieAppBackendApplication.class);
    @Value("${application.security.admin.password}")
    private String adminPassword;
    public static void main(String[] args) {
        SpringApplication.run(MovieAppBackendApplication.class, args);
    }
    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service,
            UserRepository userRepository

    ) {
        return args -> {
            if (userRepository.count() == 0) {
                var admin = RegisterRequest.builder()
                        .firstname("Admin")
                        .lastname("Admin")
                        .email("admin@mail.com")
                        .password(adminPassword)
                        .role(ADMIN)
                        .build();
                logger.info("Admin token: {}", service.register(admin).getAccessToken());
                var user = RegisterRequest.builder()
                        .firstname("user")
                        .lastname("user")
                        .email("user@mail.com")
                        .password("password")
                        .role(USER)
                        .build();
                logger.info("User token: {}", service.register(user).getAccessToken());
            }
        };
    }
}
