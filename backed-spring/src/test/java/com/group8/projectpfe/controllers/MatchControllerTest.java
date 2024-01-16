package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.MatchDto;
import com.group8.projectpfe.services.MatchService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MatchControllerTest {

    @Mock
    private MatchService matchService;

    @InjectMocks
    private MatchController matchController;

    @Test
    public void testGetMatchById() {
        // Mock data
        Integer matchId = 1;
        MatchDto matchDto = new MatchDto();
        Mockito.when(matchService.getMatchById(matchId)).thenReturn(matchDto);

        // Perform the GET request
        ResponseEntity<MatchDto> response = matchController.getMatchById(matchId);

        // Verify the result
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(matchDto, response.getBody());
    }

    @Test
    public void testGetAllMatches() {
        // Mock data
        List<MatchDto> matches = Arrays.asList(new MatchDto(), new MatchDto());
        Mockito.when(matchService.getAllMatches()).thenReturn(matches);

        // Perform the GET request
        ResponseEntity<List<MatchDto>> response = matchController.getAllMatches();

        // Verify the result
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(matches, response.getBody());
    }

    @Test
    public void testCreateMatch() {
        // Mock data
        MatchDto matchDto = new MatchDto();
        MatchDto createdMatch = new MatchDto();
        Mockito.when(matchService.createMatch(matchDto)).thenReturn(createdMatch);

        // Perform the POST request
        ResponseEntity<MatchDto> response = matchController.createMatch(matchDto);

        // Verify the result
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assert.assertEquals(createdMatch, response.getBody());
    }

    @Test
    public void testUpdateMatch() {
        // Mock data
        Integer id = 1;
        MatchDto matchDto = new MatchDto();
        matchDto.setId(id);
        MatchDto updatedMatch = new MatchDto();
        Mockito.when(matchService.updateMatch(matchDto)).thenReturn(updatedMatch);

        // Perform the PUT request
        ResponseEntity<MatchDto> response = matchController.updateMatch(id, matchDto);

        // Verify the result
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(updatedMatch, response.getBody());
    }

    @Test
    public void testDeleteMatch() {
        // Mock data
        Integer matchId = 1;

        // Perform the DELETE request
        ResponseEntity<Void> response = matchController.deleteMatch(matchId);

        // Verify the result
        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}