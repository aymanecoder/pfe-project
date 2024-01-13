package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.TeamDTO;
import com.group8.projectpfe.domain.dto.SportifDTO;
import com.group8.projectpfe.domain.dto.SportDTO;
import com.group8.projectpfe.services.Impl.ImageService;
import com.group8.projectpfe.services.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
class EquipeControllerTest {

    @Mock
    private TeamService equipeService;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private EquipeController equipeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTeams() {
        // Given
        List<TeamDTO> teams = new ArrayList<>();
        when(equipeService.getAllTeams()).thenReturn(teams);

        // When
        ResponseEntity<List<TeamDTO>> response = equipeController.getAllTeams();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(teams, response.getBody());
    }

    @Test
    void testGetTeamById_ExistingTeam() {
        // Given
        int id = 1;
        TeamDTO team = new TeamDTO();
        when(equipeService.getTeamById(id)).thenReturn(Optional.of(team));

        // When
        ResponseEntity<TeamDTO> response = equipeController.getTeamById(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(team, response.getBody());
    }

    @Test
    void testGetTeamById_NonExistingTeam() {
        // Given
        int id = 1;
        when(equipeService.getTeamById(id)).thenReturn(Optional.empty());

        // When
        ResponseEntity<TeamDTO> response = equipeController.getTeamById(id);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreateTeam() throws IOException {
        // Given
        MultipartFile logo = new MockMultipartFile("logo", new byte[0]);
        TeamDTO team = new TeamDTO();
        when(imageService.saveImage(eq(logo))).thenReturn("path/to/image.jpg");
        when(equipeService.createTeam(any(TeamDTO.class))).thenReturn(team);

        // When
        ResponseEntity<TeamDTO> response = equipeController.createTeam(logo, team);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(team, response.getBody());
//        verify(imageService).saveImage(eq(logo));
        verify(imageService, never()).deleteProfile(anyString());
    }

    @Test
    void testUpdateTeam() throws IOException {
        // Given
        int id = 1;
        MultipartFile logo = new MockMultipartFile("logo", new byte[0]);
        TeamDTO updatedTeam = new TeamDTO();
        when(imageService.saveImage(eq(logo))).thenReturn("path/to/image.jpg");
        when(equipeService.updateTeam(eq(id), any(TeamDTO.class))).thenReturn(updatedTeam);

        // When
        ResponseEntity<TeamDTO> response = equipeController.updateTeam(id, logo, updatedTeam);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTeam, response.getBody());
//        verify(imageService).saveImage(eq(logo));
        verify(imageService, never()).deleteProfile(anyString());
    }

    @Test
    void testUpdateTeam_NoLogo() throws IOException {
        // Given
        int id = 1;
        TeamDTO updatedTeam = new TeamDTO();
        when(equipeService.updateTeam(eq(id), any(TeamDTO.class))).thenReturn(updatedTeam);

        // When
//        ResponseEntity<TeamDTO> response = equipeController.updateTeam(id, null, updatedTeam);
//
//        // Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(updatedTeam, response.getBody());
//        verify(imageService, never()).saveImage(any(MultipartFile.class));
//        verify(imageService, never()).deleteProfile(anyString());
    }

    @Test
    void testDeleteTeam() throws IOException {
        // Given
        int id = 1;
        TeamDTO team = new TeamDTO();
        team.setLogoPath("path/to/image.jpg");
        when(equipeService.getTeamById(id)).thenReturn(Optional.of(team));

        // When
        ResponseEntity<String> response = equipeController.deleteTeam(id);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(equipeService).deleteTeam(id);
        verify(imageService).deleteProfile(team.getLogoPath());
    }

    @Test
    void testDeleteTeam_NonExistingTeam() throws IOException {
        // Given
        int id = 1;
        when(equipeService.getTeamById(id)).thenReturn(Optional.empty());

        // When
        ResponseEntity<String> response = equipeController.deleteTeam(id);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(equipeService, never()).deleteTeam(id);
        verify(imageService, never()).deleteProfile(anyString());
    }






}