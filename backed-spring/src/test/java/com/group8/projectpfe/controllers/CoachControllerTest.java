package com.group8.projectpfe.controllers;
import com.group8.projectpfe.domain.dto.CoachDTO;
import com.group8.projectpfe.entities.Role;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.services.CoachService;
import com.group8.projectpfe.services.Impl.ImageService;
import com.group8.projectpfe.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CoachControllerTest {

    private CoachController coachController;

    @Mock
    private CoachService coachService;

    @Mock
    private JwtService jwtService;

    @Mock
    private ImageService imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        coachController = new CoachController(coachService, jwtService, imageService);
    }

    @Test
    void testGetCoachs() {
        // Given
        List<CoachDTO> expectedCoaches = Collections.singletonList(new CoachDTO());
        when(coachService.getCoachs()).thenReturn(expectedCoaches);

        // When
        ResponseEntity<List<CoachDTO>> response = coachController.getCoachs();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCoaches, response.getBody());
    }

    @Test
    void testGetCoachByIdExistingId() {
        // Given
        Long coachId = 1L;
        CoachDTO expectedCoach = new CoachDTO();
        when(coachService.getCoachById(coachId)).thenReturn(expectedCoach);

        // When
        ResponseEntity<CoachDTO> response = coachController.getCoachById(coachId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCoach, response.getBody());
    }

    @Test
    void testGetCoachByIdNonExistingId() {
        // Given
        Long coachId = 1L;
        when(coachService.getCoachById(coachId)).thenReturn(null);

        // When
        ResponseEntity<CoachDTO> response = coachController.getCoachById(coachId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void testDeleteUserUnauthorized() {
        // Given
        Integer id = 1;

        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getPrincipal()).thenReturn(User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .role(Role.USER)
                .build());
        when(coachService.getCoachById(1L)).thenReturn(null);

        // When
        ResponseEntity<String> response = coachController.deleteUser(id);

        // Then
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertEquals("Unauthorized delete or coach not found", response.getBody());
        verifyNoInteractions(imageService);
        verifyNoInteractions(coachService);
    }

    @Test
    void testUpdateCoach() throws IOException {
        // Given
        Integer id = 1;
        MultipartFile file = new MockMultipartFile("file", new byte[0]);
        CoachDTO coachDTO = new CoachDTO();
        coachDTO.setId(1);

        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getPrincipal()).thenReturn(User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .role(Role.USER)
                .build());
        when(coachService.getCoachById(1L)).thenReturn(coachDTO);
        when(imageService.saveImage(any(MultipartFile.class))).thenReturn("path/to/image.jpg");

        // When
        ResponseEntity<String> response = coachController.updateCoach(id, file, coachDTO);

        // Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Coach updated successfully", response.getBody());
//        verify(imageService).saveImage(file);
//        verify(imageService).deleteProfile(coachDTO.getPicturePath());
//        verify(coachService).updateCoach(coachDTO);
    }

    @Test
    void testUpdateCoachUnauthorized() {
        // Given
        Integer id = 1;
        MultipartFile file = new MockMultipartFile("file", new byte[0]);

        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getPrincipal()).thenReturn(User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .role(Role.USER)
                .build());
        when(coachService.getCoachById(1L)).thenReturn(null);

        // When
        ResponseEntity<String> response = coachController.updateCoach(id, file, new CoachDTO());

        // Then
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertEquals("Unauthorized update or coach not found", response.getBody());
        verifyNoInteractions(imageService);
        verifyNoInteractions(coachService);
    }

    @Test
    void testUpdateCoachFileProcessingError() throws IOException {
        // Given
        Integer id = 1;
        MultipartFile file = new MockMultipartFile("file", new byte[0]);
        CoachDTO coachDTO = new CoachDTO();
        coachDTO.setId(1);

        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getPrincipal()).thenReturn(User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .role(Role.USER)
                .build());
        when(coachService.getCoachById(1L)).thenReturn(coachDTO);
        when(imageService.saveImage(any(MultipartFile.class))).thenThrow(new IOException());

        // When
        ResponseEntity<String> response = coachController.updateCoach(id, file, coachDTO);

        // Then
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//        assertEquals("Failed to process the image", response.getBody());
        verifyNoInteractions(imageService);
        verifyNoInteractions(coachService);
    }
}