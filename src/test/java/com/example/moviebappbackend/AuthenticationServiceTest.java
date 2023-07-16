package com.example.moviebappbackend;

import com.example.moviebappbackend.auth.AuthenticationRequest;
import com.example.moviebappbackend.auth.AuthenticationResponse;
import com.example.moviebappbackend.auth.AuthenticationService;
import com.example.moviebappbackend.auth.RegisterUserRequest;
import com.example.moviebappbackend.config.JwtService;
import com.example.moviebappbackend.exception.UsernamePasswordException;
import com.example.moviebappbackend.user.Role;
import com.example.moviebappbackend.user.User;
import com.example.moviebappbackend.user.UserRepository;
import com.example.moviebappbackend.user.token.Token;
import com.example.moviebappbackend.user.token.TokenRepository;
import com.example.moviebappbackend.user.token.TokenType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testRegisterUser_Success() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("password123");
        request.setRole(Role.valueOf("USER"));

        String encodedPassword = "encodedPassword123";
        when(passwordEncoder.encode(request.getPassword())).thenReturn(encodedPassword);

        User user = new User();
        user.setId(1L);
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setRole(request.getRole());

        when(userRepository.save(any(User.class))).thenReturn(user);

        String jwtToken = "sampleJwtToken";
        String refreshToken = "sampleRefreshToken";
        when(jwtService.generateToken(user)).thenReturn(jwtToken);
        when(jwtService.generateRefreshToken(user)).thenReturn(refreshToken);

        AuthenticationResponse response = authenticationService.register(request);

        assertNotNull(response);
    }

    @Test
     void testAuthenticate_Success() {
        String email = "john.doe@example.com";
        String password = "password123";

        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail(email);
        request.setPassword(password);

        User user = new User();
        user.setId(1L);
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        String jwtToken = "sampleJwtToken";
        String refreshToken = "sampleRefreshToken";
        when(jwtService.generateToken(user)).thenReturn(jwtToken);
        when(jwtService.generateRefreshToken(user)).thenReturn(refreshToken);

        AuthenticationResponse response = authenticationService.authenticate(request);

        assertNotNull(response);
        assertEquals(jwtToken, response.getAccessToken());
        assertEquals(refreshToken, response.getRefreshToken());
    }

    @Test
     void testAuthenticate_InvalidCredentials() {
        String email = "john.doe@example.com";
        String password = "invalidPassword";

        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail(email);
        request.setPassword(password);

        when(authenticationManager.authenticate(any())).thenThrow(new UsernamePasswordException("Invalid username or password"));

        assertThrows(UsernamePasswordException.class, () -> authenticationService.authenticate(request));
    }
}

