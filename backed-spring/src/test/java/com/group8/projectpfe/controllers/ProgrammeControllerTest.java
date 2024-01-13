package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.ProgrammeDTO;
import com.group8.projectpfe.services.Impl.ImageService;
import com.group8.projectpfe.services.ProgrammeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ProgrammeControllerTest {

    @Mock
    private ProgrammeService programmeService;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private ProgrammeController programmeController;

    @Test
    public void testGetAllProgrammes() {
        // Mock data
        List<ProgrammeDTO> programmes = Arrays.asList(new ProgrammeDTO(), new ProgrammeDTO());
        Mockito.when(programmeService.getAllProgrammes()).thenReturn(programmes);

        // Perform the GET request
        ResponseEntity<List<ProgrammeDTO>> response = programmeController.getAllProgrammes();

        // Verify the result
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(programmes, response.getBody());
    }

    @Test
    public void testGetProgrammeById() {
        // Mock data
        Long id = 1L;
        Optional<ProgrammeDTO> programme = Optional.of(new ProgrammeDTO());
        Mockito.when(programmeService.getProgrammeById(id)).thenReturn(programme);

        // Perform the GET request
        ResponseEntity<ProgrammeDTO> response = programmeController.getProgrammeById(id);

        // Verify the result
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(programme.get(), response.getBody());
    }

    @Test
    public void testGetProgrammeById_NotFound() {
        // Mock data
        Long id = 1L;
        Optional<ProgrammeDTO> programme = Optional.empty();
        Mockito.when(programmeService.getProgrammeById(id)).thenReturn(programme);

        // Perform the GET request
        ResponseEntity<ProgrammeDTO> response = programmeController.getProgrammeById(id);

        // Verify the result
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assert.assertNull(response.getBody());
    }

    @Test
    public void testCreateProgramme() throws IOException {
        // Mock data
        MultipartFile program = Mockito.mock(MultipartFile.class);
        ProgrammeDTO programmeDetails = new ProgrammeDTO();
        ProgrammeDTO createdProgramme = new ProgrammeDTO();
        Mockito.when(imageService.saveImage(program)).thenReturn("imagePath");
        Mockito.when(programmeService.createProgramme(programmeDetails)).thenReturn(createdProgramme);

        // Perform the POST request
        ResponseEntity<ProgrammeDTO> response = programmeController.createProgramme(program, programmeDetails);

        // Verify the result
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assert.assertEquals(createdProgramme, response.getBody());
    }

    @Test
    public void testCreateProgramme_FileProcessingError() throws IOException {
        // Mock data
        MultipartFile program = Mockito.mock(MultipartFile.class);
        ProgrammeDTO programmeDetails = new ProgrammeDTO();
        Mockito.when(imageService.saveImage(program)).thenThrow(new IOException());

        // Perform the POST request
        ResponseEntity<ProgrammeDTO> response = programmeController.createProgramme(program, programmeDetails);

        // Verify the result
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assert.assertNull(response.getBody());
    }

    @Test
    public void testUpdateProgramme() throws IOException {
        // Mock data
        Long id = 1L;
        MultipartFile program = Mockito.mock(MultipartFile.class);
        ProgrammeDTO updatedProgrammeDetails = new ProgrammeDTO();
        updatedProgrammeDetails.setPicturePath("oldImagePath");
        ProgrammeDTO updatedProgramme = new ProgrammeDTO();
        Mockito.when(imageService.saveImage(program)).thenReturn("newImagePath");
        Mockito.when(programmeService.updateProgramme(id, updatedProgrammeDetails)).thenReturn(updatedProgramme);

        // Perform the PUT request
        ResponseEntity<ProgrammeDTO> response = programmeController.updateProgramme(id, program, updatedProgrammeDetails);

        // Verify the result
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(updatedProgramme, response.getBody());
    }

    @Test
    public void testUpdateProgramme_FileProcessingError() throws IOException {
        // Mock data
        Long id = 1L;
        MultipartFile program = Mockito.mock(MultipartFile.class);
        ProgrammeDTO updatedProgrammeDetails = new ProgrammeDTO();
        Mockito.when(imageService.saveImage(program)).thenThrow(new IOException());

        // Perform the PUT request
        ResponseEntity<ProgrammeDTO> response = programmeController.updateProgramme(id, program, updatedProgrammeDetails);

        // Verify the result
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assert.assertNull(response.getBody());
    }

    @Test
    public void testUpdateProgramme_NotFound() throws IOException {
        // Mock data
        Long id = 1L;
        MultipartFile program = Mockito.mock(MultipartFile.class);
        ProgrammeDTO updatedProgrammeDetails = new ProgrammeDTO();
        Mockito.when(imageService.saveImage(program)).thenReturn("newImagePath");
        Mockito.when(programmeService.updateProgramme(id, updatedProgrammeDetails)).thenReturn(null);

        // Perform the PUT request
        ResponseEntity<ProgrammeDTO> response = programmeController.updateProgramme(id, program, updatedProgrammeDetails);

        // Verify the result
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assert.assertNull(response.getBody());
    }

    @Test
    public void testDeleteProgramme() throws IOException {
        // Mock data
        Long id = 1L;
        ProgrammeDTO programmeDTO = Mockito.mock(ProgrammeDTO.class);
        Mockito.when(programmeService.getProgrammeById(id)).thenReturn(Optional.of(programmeDTO));

        // Perform the DELETE request
        ResponseEntity<String> response = programmeController.deleteProgramme(id);

        // Verify the result
        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assert.assertNull(response.getBody());

        // Verify that deleteProfile and deleteProgramme methods are called
        Mockito.verify(imageService).deleteProfile(programmeDTO.getPicturePath());
        Mockito.verify(programmeService).deleteProgramme(id);
    }

    @Test
    public void testDeleteProgramme_NotFound() throws IOException {
        // Mock data
        Long id = 1L;
        Mockito.when(programmeService.getProgrammeById(id)).thenReturn(Optional.empty());

        // Perform the DELETE request
        ResponseEntity<String> response = programmeController.deleteProgramme(id);

        // Verify the result
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assert.assertEquals("Unauthorized delete or sportif not found", response.getBody());

        // Verify that deleteProfile and deleteProgramme methods are not called
        Mockito.verify(imageService, Mockito.never()).deleteProfile(Mockito.anyString());
        Mockito.verify(programmeService, Mockito.never()).deleteProgramme(Mockito.anyLong());
    }

    @Test
    public void testSearchByTitle() {
        // Mock data
        String title = "example";
        List<ProgrammeDTO> programmes = Arrays.asList(new ProgrammeDTO(), new ProgrammeDTO());
        Mockito.when(programmeService.searchByTitle(title)).thenReturn(programmes);

        // Perform the GET request
        ResponseEntity<List<ProgrammeDTO>> response = programmeController.searchByTitle(title);

        // Verify the result
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(programmes, response.getBody());
    }

    @Test
    public void testGetProgramsByTypeProgram() {
        // Mock data
        String typeProgram = "example";
        List<ProgrammeDTO> programmes = Arrays.asList(new ProgrammeDTO(), new ProgrammeDTO());
        Mockito.when(programmeService.getProgramsByTypeProgram(typeProgram)).thenReturn(programmes);

        // Perform the GET request
        ResponseEntity<List<ProgrammeDTO>> response = programmeController.getProgramsByTypeProgram(typeProgram);

        // Verify the result
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(programmes, response.getBody());
    }
}