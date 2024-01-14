package com.group8.projectpfe.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group8.projectpfe.domain.dto.AuthenticationRequest;
import com.group8.projectpfe.domain.dto.AuthenticationResponse;
import com.group8.projectpfe.domain.dto.RegisterRequest;
import com.group8.projectpfe.entities.Role;
import com.group8.projectpfe.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestClientException;

import static org.mockito.Mockito.*;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private MockMvc mockMvc;  // Ensure this line is present

    public AuthControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).setControllerAdvice().build();


        // Print debug information
        System.out.println("authService: " + authService);
    }
    @Test
    void register() throws Exception {
        // Given
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstName("admin")
                .lastName("admin")
                .email("test@example.com")
                .password("admin123")
                .role(Role.USER)
                .build();

        AuthenticationResponse expectedResponse = AuthenticationResponse.builder()
                .accessToken("token123")
                .build();

//        when(authService.register(any(RegisterRequest.class))).thenReturn(expectedResponse);

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registerRequest)))
                .andExpect(status().isOk());
//                .andExpect(MockMvcResultMatchers.content().json(asJsonString(expectedResponse)));

        // Then
//        verify(authService, times(1)).register(any(RegisterRequest.class));
        verifyNoMoreInteractions(authService);
    }

    @Test
    public void testAuthenticate() throws Exception {
        // Given
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email("test@example.com")
                .password("admin123")
                .build();

        AuthenticationResponse expectedResponse = AuthenticationResponse.builder()
                .accessToken("token123")
                .build();

//        when(authService.authenticate(authenticationRequest)).thenReturn(expectedResponse);

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(authenticationRequest)))
                .andExpect(status().isOk());
//                .andExpect((ResultMatcher) jsonPath("$.accessToken").value("token123"));

        // Then
//        verify(authService, times(1)).authenticate(authenticationRequest);
        verifyNoMoreInteractions(authService);
    }


    private String asJsonString(Object object) throws JsonProcessingException  {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

}
