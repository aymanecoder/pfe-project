package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.ChallengeDTO;
import com.group8.projectpfe.repositories.UserRepository;
import com.group8.projectpfe.services.ChallengeService;
import com.group8.projectpfe.services.Impl.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ChallengeControllerTest {

    private ChallengeController challengeController;

    @Mock
    private ChallengeService challengeService;

    @Mock
    private ImageService imageService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        challengeController = new ChallengeController(challengeService, imageService,userRepository);
    }

    @Test
    void testGetAllChallenges() {
        // Given
        List<ChallengeDTO> expectedChallenges = Collections.singletonList(new ChallengeDTO());
        when(challengeService.getAllChallenges()).thenReturn(expectedChallenges);

        // When
        ResponseEntity<List<ChallengeDTO>> response = challengeController.getAllChallenges();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedChallenges, response.getBody());
    }

    @Test
    void testGetChallengeByIdExistingId() {
        // Given
        int challengeId = 1;
        ChallengeDTO expectedChallenge = new ChallengeDTO();
        when(challengeService.getChallengeById(challengeId)).thenReturn(Optional.of(expectedChallenge));

        // When
        ResponseEntity<ChallengeDTO> response = challengeController.getChallengeById(challengeId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedChallenge, response.getBody());
    }

    @Test
    void testGetChallengeByIdNonExistingId() {
        // Given
        int challengeId = 1;
        when(challengeService.getChallengeById(challengeId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<ChallengeDTO> response = challengeController.getChallengeById(challengeId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

        @Test
        void testCreateChallenge() throws IOException {
            // Given
            MultipartFile logo = new MockMultipartFile("logo", new byte[0]);
            ChallengeDTO challengeDTO = new ChallengeDTO();
            ChallengeDTO createdChallenge = new ChallengeDTO();
            when(imageService.saveImage(any(MultipartFile.class))).thenReturn("path/to/image");
            when(challengeService.createChallenge(any(ChallengeDTO.class))).thenReturn(createdChallenge);

            // When
            ResponseEntity<ChallengeDTO> response = challengeController.createChallenge(logo, challengeDTO);

            // Then
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(createdChallenge, response.getBody());
//            verify(imageService).saveImage(logo);
            verify(challengeService).createChallenge(challengeDTO);
        }

    @Test
    void testUpdateChallenge() throws IOException {
        // Given
        int challengeId = 1;
        MultipartFile logo = new MockMultipartFile("logo", new byte[0]);
        ChallengeDTO updatedChallengeDTO = new ChallengeDTO();
        ChallengeDTO updatedChallenge = new ChallengeDTO();
        when(imageService.saveImage(any(MultipartFile.class))).thenReturn("path/to/image");
        when(challengeService.updateChallenge(eq(challengeId), any(ChallengeDTO.class))).thenReturn(updatedChallenge);

        // When
        ResponseEntity<ChallengeDTO> response = challengeController.updateChallenge(challengeId, logo, updatedChallengeDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedChallenge, response.getBody());
//        verify(imageService).saveImage(logo);
        verify(challengeService).updateChallenge(challengeId, updatedChallengeDTO);
    }

    @Test
    void testUpdateChallengeWithInvalidId() throws IOException {
        // Given
        int challengeId = 1;
        MultipartFile logo = new MockMultipartFile("logo", new byte[0]);
        ChallengeDTO updatedChallengeDTO = new ChallengeDTO();
        when(imageService.saveImage(any(MultipartFile.class))).thenReturn("path/to/image");
        when(challengeService.updateChallenge(eq(challengeId), any(ChallengeDTO.class))).thenReturn(null);

        // When
        ResponseEntity<ChallengeDTO> response = challengeController.updateChallenge(challengeId, logo, updatedChallengeDTO);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        verify(imageService).saveImage(logo);
        verify(challengeService).updateChallenge(challengeId, updatedChallengeDTO);
    }

    @Test
    void testDeleteChallenge() throws IOException {
        // Given
        int challengeId = 1;
        ChallengeDTO challengeDTO = new ChallengeDTO();
        when(challengeService.getChallengeById(challengeId)).thenReturn(Optional.of(challengeDTO));

        // When
        ResponseEntity<String> response = challengeController.deleteChallenge(challengeId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(imageService).deleteProfile(challengeDTO.getLogoPath());
        verify(challengeService).deleteChallenge(challengeId);
    }

    @Test
    void testDeleteChallengeWithInvalidId() throws IOException {
        // Given
        int challengeId = 1;
        when(challengeService.getChallengeById(challengeId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<String> response = challengeController.deleteChallenge(challengeId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verifyNoInteractions(imageService);
//        verifyNoInteractions(challengeService);
    }
}