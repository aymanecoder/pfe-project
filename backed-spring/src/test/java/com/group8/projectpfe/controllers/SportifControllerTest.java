package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.SportifDTO;
import com.group8.projectpfe.entities.User;

import com.group8.projectpfe.services.Impl.ImageService;
import com.group8.projectpfe.services.SportifService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class SportifControllerTest {

    @Mock
    private SportifService sportifService;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private SportifController sportifController;

    @Before
    public void setup() {
        // Set up mock data or initialize dependencies if required

        // Mock data
        SportifDTO sportif1 = SportifDTO.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .address("123 Main Street")
                .age(25)
                .taille(180)
                .poids(75)
                .PicturePath("/path/to/image1.jpg")
                .build();

        SportifDTO sportif2 = SportifDTO.builder()
                .id(2)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .address("456 Elm Street")
                .age(30)
                .taille(165)
                .poids(60)
                .PicturePath("/path/to/image2.jpg")
                .build();

        Mockito.when(sportifService.getSportifs())
                .thenReturn(Arrays.asList(sportif1, sportif2));

        Mockito.when(sportifService.getSportifById(1L))
                .thenReturn(sportif1);

//        Mockito.when(sportifService.getSportifById(2L))
//                .thenReturn(sportif2);
    }

    @Test
    public void testGetSportifs() {
        // Perform the GET request
        ResponseEntity<List<SportifDTO>> response = sportifController.getSportifs();

        // Verify the result
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(2, response.getBody().size());

        // Verify that the service method was called
        Mockito.verify(sportifService, Mockito.times(1)).getSportifs();
    }

    @Test
    public void testGetSportifById() {
        // Perform the GET request
        ResponseEntity<SportifDTO> response = sportifController.getSportifById(1L);

        // Verify the result
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("John", response.getBody().getFirstName());

        // Verify that the service method was called
        Mockito.verify(sportifService, Mockito.times(1)).getSportifById(1L);
    }

    @Test
    public void testGetSportifById_NotFound() {
        // Perform the GET request
        ResponseEntity<SportifDTO> response = sportifController.getSportifById(3L);

        // Verify the result
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assert.assertNull(response.getBody());

        // Verify that the service method was called
        Mockito.verify(sportifService, Mockito.times(1)).getSportifById(3L);
    }

    @Test
    public void testDeleteUser() throws IOException {
        // Mock data
        Integer id = 1;
        SportifDTO sportifDTO = new SportifDTO();
        sportifDTO.setId(id);
        User user = new User();
        user.setId(id);
//        Mockito.when(sportifService.getSportifById(Long.valueOf(id))).thenReturn(sportifDTO);
//        Mockito.when(imageService.deleteProfile(sportifDTO.getPicturePath())).thenReturn(true);

        // Perform the DELETE request
        ResponseEntity<String> response = sportifController.deleteUser(id);

        // Verify the result
//        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
//        Assert.assertEquals("User deleted successfully", response.getBody());

        // Verify that the service methods were called
//        Mockito.verify(sportifService, Mockito.times(1)).getSportifById(Long.valueOf(id));
//        Mockito.verify(imageService, Mockito.times(1)).deleteProfile(sportifDTO.getPicturePath());
//        Mockito.verify(sportifService, Mockito.times(1)).deleteSportif(id, user.getId());
    }

    @Test
    public void testDeleteUser_Unauthorized() throws IOException {
        // Mock data
        Integer id = 1;
//        Mockito.doNothing().when(sportifService).deleteSportif(Mockito.anyInt(), Mockito.anyInt());
        ResponseEntity<String> response = sportifController.deleteUser(id);

        // Verify the result
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
//        Assert.assertEquals("Unauthorized to delete the user", response.getBody());

        // Verify that the service methods were called
//        Mockito.verify(sportifService, Mockito.times(1)).getSportifById(Long.valueOf(id));
        Mockito.verify(imageService, Mockito.never()).deleteProfile(Mockito.anyString());
        Mockito.verify(sportifService, Mockito.never()).deleteSportif(Mockito.anyInt(), Mockito.anyInt());
    }



}