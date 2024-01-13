package com.group8.projectpfe.controllers;

import com.group8.projectpfe.controllers.SportController;
import com.group8.projectpfe.domain.dto.SportDTO;
import com.group8.projectpfe.services.Impl.ImageService;
import com.group8.projectpfe.services.SportService;
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
public class SportControllerTest {

    @Mock
    private SportService sportService;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private SportController sportController;

    @Test
    public void testGetAllSports() {
        // Mock data
        List<SportDTO> sports = Arrays.asList(new SportDTO(), new SportDTO());
        Mockito.when(sportService.getAllSports()).thenReturn(sports);

        // Perform the GET request
        ResponseEntity<List<SportDTO>> response = sportController.getAllSports();

        // Verify the result
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(sports, response.getBody());
    }

    @Test
    public void testGetSportById() {
        // Mock data
        int id = 1;
        Optional<SportDTO> sport = Optional.of(new SportDTO());
        Mockito.when(sportService.getSportById(id)).thenReturn(sport);

        // Perform the GET request
        ResponseEntity<SportDTO> response = sportController.getSportById(id);

        // Verify the result
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(sport.get(), response.getBody());
    }

    @Test
    public void testGetSportById_NotFound() {
        // Mock data
        int id = 1;
        Optional<SportDTO> sport = Optional.empty();
        Mockito.when(sportService.getSportById(id)).thenReturn(sport);

        // Perform the GET request
        ResponseEntity<SportDTO> response = sportController.getSportById(id);

        // Verify the result
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assert.assertNull(response.getBody());
    }

    @Test
    public void testCreateSport() throws IOException {
        // Mock data
        MultipartFile logo = Mockito.mock(MultipartFile.class);
        SportDTO sportDetails = new SportDTO();
        SportDTO createdSport = new SportDTO();
        Mockito.when(imageService.saveImage(logo)).thenReturn("imagePath");
        Mockito.when(sportService.createSport(sportDetails)).thenReturn(createdSport);

        // Perform the POST request
        ResponseEntity<SportDTO> response = sportController.createSport(logo, sportDetails);

        // Verify the result
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assert.assertEquals(createdSport, response.getBody());
    }

    @Test
    public void testCreateSport_FileProcessingError() throws IOException {
        // Mock data
        MultipartFile logo = Mockito.mock(MultipartFile.class);
        SportDTO sportDetails = new SportDTO();
        Mockito.when(imageService.saveImage(logo)).thenThrow(new IOException());

        // Perform the POST request
        ResponseEntity<SportDTO> response = sportController.createSport(logo, sportDetails);

        // Verify the result
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assert.assertNull(response.getBody());
    }

    @Test
    public void testUpdateSport() throws IOException {
        // Mock data
        int id = 1;
        MultipartFile logo = Mockito.mock(MultipartFile.class);
        SportDTO updatedSportDetails = new SportDTO();
        updatedSportDetails.setLogoPath("oldImagePath");
        SportDTO updatedSport = new SportDTO();
        Mockito.when(imageService.saveImage(logo)).thenReturn("newImagePath");
        Mockito.when(sportService.updateSport(id, updatedSportDetails)).thenReturn(updatedSport);

        // Perform the PUT request
        ResponseEntity<SportDTO> response = sportController.updateSport(id, logo, updatedSportDetails);

        // Verify the result
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(updatedSport, response.getBody());
    }

    @Test
    public void testUpdateSport_FileProcessingError() throws IOException {
        // Mock data
        int id = 1;
        MultipartFile logo = Mockito.mock(MultipartFile.class);
        SportDTO updatedSportDetails = new SportDTO();
        Mockito.when(imageService.saveImage(logo)).thenThrow(new IOException());

        // Perform the PUT request
        ResponseEntity<SportDTO> response = sportController.updateSport(id, logo, updatedSportDetails);

        // Verify the result
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assert.assertNull(response.getBody());
    }

    @Test
    public void testUpdateSport_NotFound() throws IOException {
        // Mock data
        int id = 1;
        MultipartFile logo = Mockito.mock(MultipartFile.class);
        SportDTO updatedSportDetails = new SportDTO();
        Mockito.when(imageService.saveImage(logo)).thenReturn("newImagePath");
        Mockito.when(sportService.updateSport(id, updatedSportDetails)).thenReturn(null);

        // Perform the PUT request
        ResponseEntity<SportDTO> response = sportController.updateSport(id, logo, updatedSportDetails);

        // Verify the result
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assert.assertNull(response.getBody());
    }
    @Test
    public void testDeleteSport() throws IOException {
        // Mock data
        int id = 1;
        SportDTO sportDTO = new SportDTO();
        sportDTO.setLogoPath("imagePath");
        Mockito.when(sportService.getSportById(id)).thenReturn(Optional.of(sportDTO));

        // Perform the DELETE request
        ResponseEntity<String> response = sportController.deleteSport(id);

        // Verify the result
        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assert.assertNull(response.getBody());

        // Verify that the image was deleted and the sport was deleted
        Mockito.verify(imageService, Mockito.times(1)).deleteProfile(sportDTO.getLogoPath());
        Mockito.verify(sportService, Mockito.times(1)).deleteSport(id);
    }


}
