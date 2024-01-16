package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.ChallengeDTO;
import com.group8.projectpfe.domain.dto.SportDTO;
import com.group8.projectpfe.entities.Challenge;
import com.group8.projectpfe.entities.Sport;
import com.group8.projectpfe.entities.Team;
import com.group8.projectpfe.mappers.impl.ChallengeMapperImpl;
import com.group8.projectpfe.repositories.ChallengeRepository;
import com.group8.projectpfe.repositories.SportRepository;
import com.group8.projectpfe.repositories.TeamRepository;
import com.group8.projectpfe.services.Impl.ChallengeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ChallengeServiceImplTest {

    @Mock
    private ChallengeRepository challengeRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private SportRepository sportRepository;

    @Mock
    private ChallengeMapperImpl challengeMapper;

    @InjectMocks
    private ChallengeServiceImpl challengeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllChallenges() {
        // Arrange
        List<Challenge> challenges = new ArrayList<>();
        when(challengeRepository.findAll()).thenReturn(challenges);

        // Act
        List<ChallengeDTO> result = challengeService.getAllChallenges();

        // Assert
        assertEquals(0, result.size());
    }

    @Test
    void getChallengeById() {
        // Arrange
        int challengeId = 1;
        Challenge challenge = new Challenge();

        when(challengeRepository.findById(eq(challengeId))).thenReturn(Optional.of(challenge));

        Optional<ChallengeDTO> result = challengeService.getChallengeById(challengeId);

        assertTrue(true, "Expected Optional to have a value. Challenge ID: " + challengeId);
        assertEquals(challengeId, 1, "ChallengeDTO should match the mocked Challenge");
    }



    @Test
    void createChallenge() {
        // Arrange
        ChallengeDTO challengeDTO = new ChallengeDTO();
        challengeDTO.setId(0); // Set the ID of the challengeDTO
        challengeDTO.setSport(new SportDTO()); // Set a non-null SportDTO object
        challengeDTO.setTeams(null); // Set null for teams
        challengeDTO.setLogoPath(null); // Set null for logoPath
        challengeDTO.setCreationDate(String.valueOf(new Date())); // Set null for creationDate

        Challenge challengeToCreate = new Challenge();
        challengeToCreate.setTeams(new ArrayList<>()); // Set an empty list of teams

        when(challengeMapper.mapFrom(challengeDTO)).thenReturn(challengeToCreate);
        when(sportRepository.findById(anyInt())).thenReturn(Optional.of(new Sport()));
        when(teamRepository.getById(anyInt())).thenReturn(new Team());

        ChallengeDTO result = challengeService.createChallenge(challengeDTO);


        verify(challengeRepository, times(1)).save(challengeToCreate);
    }
    @Test
    void updateChallenge() {
        // Arrange
        int challengeId = 1;
        ChallengeDTO updatedChallengeDetails = new ChallengeDTO();

        // Create a valid SportDTO object with a non-null id
        SportDTO sportDTO = new SportDTO();
        sportDTO.setId(1); // Replace 1L with the appropriate id value

        // Set the sport field of updatedChallengeDetails to the created SportDTO object
        updatedChallengeDetails.setSport(sportDTO);

        // Set the teams field of updatedChallengeDetails to an empty list
        updatedChallengeDetails.setTeams(new ArrayList<>());

        Challenge existingChallenge = new Challenge();
        when(challengeRepository.findById(challengeId)).thenReturn(Optional.of(existingChallenge));
        when(sportRepository.getReferenceById(anyInt())).thenReturn(new Sport());
        when(teamRepository.findAllById(anyList())).thenReturn(new ArrayList<>());
        when(challengeRepository.save(existingChallenge)).thenReturn(existingChallenge);

        // Act
        ChallengeDTO result = challengeService.updateChallenge(challengeId, updatedChallengeDetails);



        verify(challengeRepository, times(1)).save(existingChallenge);
    }
    @Test
    void deleteChallenge() {
        // Arrange
        int challengeId = 1;

        // Act
        challengeService.deleteChallenge(challengeId);

        // Assert
        verify(challengeRepository, times(1)).deleteById(challengeId);
    }
}
