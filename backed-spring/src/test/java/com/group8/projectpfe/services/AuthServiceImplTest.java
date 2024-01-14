package com.group8.projectpfe.services;
import com.group8.projectpfe.domain.dto.AuthenticationRequest;
import com.group8.projectpfe.domain.dto.AuthenticationResponse;
import com.group8.projectpfe.domain.dto.RegisterRequest;
import com.group8.projectpfe.entities.Role;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.repositories.UserRepository;
import com.group8.projectpfe.security.Token;
import com.group8.projectpfe.security.TokenRepository;
import com.group8.projectpfe.services.Impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {
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

    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthServiceImpl(
                userRepository,
                tokenRepository,
                passwordEncoder,
                jwtService,
                authenticationManager
        );
    }

    @Test
    void register_ShouldReturnAuthenticationResponse() {
        // Arrange
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password")
                .role(Role.USER)
                .build();

        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password("hashedPassword") // Replace with appropriate hashed password
                .role(registerRequest.getRole())
                .build();

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn("accessToken");

        // Act
        AuthenticationResponse response = authService.register(registerRequest);

        // Assert
        assertEquals("accessToken", response.getAccessToken());
        verify(userRepository, times(1)).save(any(User.class));
        verify(jwtService, times(1)).generateToken(any(User.class));
        verify(tokenRepository, times(1)).save(any(Token.class));
    }

    @Test
    void authenticate_ShouldReturnAuthenticationResponse() {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email("john.doe@example.com")
                .password("password")
                .build();
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email(authenticationRequest.getEmail())
                .password("hashedPassword") // Replace with appropriate hashed password
                .role(Role.valueOf("USER"))
                .build();

        when(userRepository.findByEmail(authenticationRequest.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(User.class))).thenReturn("accessToken");

        // Act
        AuthenticationResponse response = authService.authenticate(authenticationRequest);

        // Assert
        assertEquals("accessToken", response.getAccessToken());
        verify(authenticationManager, times(1)).authenticate(any());
        verify(userRepository, times(1)).findByEmail(authenticationRequest.getEmail());
        verify(jwtService, times(1)).generateToken(any(User.class));
        verify(tokenRepository, times(1)).save(any(Token.class));
    }

    // Add more tests for other methods if necessary
}
