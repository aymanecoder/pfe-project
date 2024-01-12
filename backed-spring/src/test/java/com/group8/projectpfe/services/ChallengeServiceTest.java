package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.ChallengeDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ChallengeServiceTest {

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

        // Mock the repository to return the challenge when findById is called
        when(challengeRepository.findById(challengeId)).thenReturn(Optional.of(challenge));

        // Act
        Optional<ChallengeDTO> result = challengeService.getChallengeById(challengeId);

        // Assert
        assertTrue(result.isPresent(), "Expected Optional to have a value. Challenge ID: " + challengeId);
        assertEquals(challenge, result.orElse(null), "ChallengeDTO should match the mocked Challenge");
    }



    @Test
    void createChallenge() {
        // Arrange
        ChallengeDTO challengeDTO = new ChallengeDTO();
        Challenge challengeToCreate = new Challenge();
        when(challengeMapper.mapFrom(challengeDTO)).thenReturn(challengeToCreate);
        when(sportRepository.findById(anyInt())).thenReturn(Optional.of(new Sport()));
        when(teamRepository.getById(anyInt())).thenReturn(new Team());

        // Act
        ChallengeDTO result = challengeService.createChallenge(challengeDTO);

        // Assert
        assertEquals(challengeDTO, result);
        verify(challengeRepository, times(1)).save(challengeToCreate);
    }

    @Test
    void updateChallenge() {
        // Arrange
        int challengeId = 1;
        ChallengeDTO updatedChallengeDetails = new ChallengeDTO();
        Challenge existingChallenge = new Challenge();
        when(challengeRepository.findById(challengeId)).thenReturn(Optional.of(existingChallenge));
        when(sportRepository.getReferenceById(anyInt())).thenReturn(new Sport());
        when(teamRepository.findAllById(anyList())).thenReturn(new ArrayList<>());

        // Act
        ChallengeDTO result = challengeService.updateChallenge(challengeId, updatedChallengeDetails);

        // Assert
        assertEquals(updatedChallengeDetails, result);
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
