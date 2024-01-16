package com.group8.projectpfe.services;

import com.group8.projectpfe.security.Token;
import com.group8.projectpfe.security.TokenRepository;
import com.group8.projectpfe.services.Impl.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class LogoutServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private LogoutService logoutService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogout() {
        // Mock the token retrieval from the request header
        when(request.getHeader("Authorization")).thenReturn("Bearer token123");

        // Mock the token retrieval from the token repository
        Token storedToken = Token.builder().token("token123").build();
        when(tokenRepository.findByToken("token123")).thenReturn(Optional.of(storedToken));

        // Mock the authentication object
        Authentication authentication = new UsernamePasswordAuthenticationToken("user123", "password");

        // Call the logout method
        logoutService.logout(request, response, authentication);

        // Verify that the token is marked as expired and revoked
        assertTrue(storedToken.isExpired());
        assertTrue(storedToken.isRevoked());

        // Verify that the token repository save method is called
        verify(tokenRepository, times(1)).save(storedToken);

//        verify(times(1));
//        SecurityContextHolder.clearContext();

    }
}