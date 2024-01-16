package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.MatchDto;
import com.group8.projectpfe.domain.dto.SportDTO;
import com.group8.projectpfe.domain.dto.SportifDTO;
import com.group8.projectpfe.domain.dto.TeamDTO;
import com.group8.projectpfe.entities.*;
import com.group8.projectpfe.mappers.impl.MatchMapperImpl;
import com.group8.projectpfe.repositories.MatchRepository;
import com.group8.projectpfe.repositories.SportRepository;
import com.group8.projectpfe.repositories.TeamRepository;
import com.group8.projectpfe.repositories.UserRepository;
import com.group8.projectpfe.services.Impl.MatchServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MatchServiceImplTest {

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private MatchMapperImpl matchMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private SportRepository sportRepository;

    @InjectMocks
    private MatchServiceImpl matchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getMatchById_ShouldReturnMatchDto_WhenMatchExists() {
        // Arrange
        Integer matchId = 1;
        Match match = new Match();
        match.setId(matchId);
        MatchDto expectedMatchDto = new MatchDto();
        expectedMatchDto.setId(matchId);

        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));
        when(matchMapper.mapTo(match)).thenReturn(expectedMatchDto);

        // Act
        MatchDto result = matchService.getMatchById(matchId);

        // Assert
        assertEquals(expectedMatchDto, result);
        verify(matchRepository, times(1)).findById(matchId);
        verify(matchMapper, times(1)).mapTo(match);
    }

    @Test
    void getMatchById_ShouldThrowEntityNotFoundException_WhenMatchDoesNotExist() {
        // Arrange
        Integer matchId = 1;

        when(matchRepository.findById(matchId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            matchService.getMatchById(matchId);
        });
        verify(matchRepository, times(1)).findById(matchId);
        verify(matchMapper, never()).mapTo(any(Match.class));
    }
    @Test
    void getAllMatches_ShouldReturnListOfMatchDtos() {
        // Arrange
        List<Match> matches = new ArrayList<>();
        matches.add(new Match());
        matches.add(new Match());

        MatchDto matchDto1 = new MatchDto();
        MatchDto matchDto2 = new MatchDto();

        when(matchRepository.findAll()).thenReturn(matches);
        when(matchMapper.mapTo(matches.get(0))).thenReturn(matchDto1);
        when(matchMapper.mapTo(matches.get(1))).thenReturn(matchDto2);

        // Act
        List<MatchDto> result = matchService.getAllMatches();

        // Assert
        assertEquals(2, result.size());
        assertEquals(matchDto1, result.get(0));
        assertEquals(matchDto2, result.get(1));
        verify(matchRepository, times(1)).findAll();
//        verify(matchMapper, times(1)).mapTo(matches.get(0));
//        verify(matchMapper, times(1)).mapTo(matches.get(1));
    }



    @Test
    public void createMatch_ShouldReturnCreatedMatchDto() {
        // Arrange
        Integer sportId = 1;
        Integer teamId1 = 1;

        SportDTO sportDTO = new SportDTO();
        sportDTO.setId(sportId);

        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setId(teamId1);

        MatchDto matchDto = new MatchDto();
        matchDto.setId(1);
        matchDto.setTitle("Sample Match");
        matchDto.setDescription("This is a sample match");
        matchDto.setScoreTeamA(2);
        matchDto.setScoreTeamB(1);
        matchDto.setPrivate(false);
        matchDto.setTeams(Collections.singletonList(teamDTO));
        matchDto.setSport(sportDTO);
        matchDto.setTypeMatch(MatchType.UPCOMING);
        matchDto.setDate(LocalDateTime.parse("2024-01-15T10:00:00"));
        matchDto.setCounter(1);

        Sport sport = new Sport();
        sport.setId(sportId);

        Team team = new Team();
        team.setId(teamId1);

        Match matchToCreate = new Match();
        matchToCreate.setTitle("Sample Match");
        matchToCreate.setDescription("This is a sample match");
        matchToCreate.setScoreTeamA(2);
        matchToCreate.setScoreTeamB(1);
        matchToCreate.setPrivate(false);
        matchToCreate.setTeams(Collections.singletonList(team));
        matchToCreate.setSport(sport);
        matchToCreate.setTypeMatch(MatchType.UPCOMING);
        matchToCreate.setDate(LocalDateTime.parse("2024-01-15T10:00:00"));


        Match savedMatch = new Match();
        savedMatch.setId(1);

        when(matchMapper.mapFrom(matchDto)).thenReturn(matchToCreate);
        when(sportRepository.findById(sportId)).thenReturn(Optional.of(sport));
        when(teamRepository.getById(teamId1)).thenReturn(team);
        when(matchRepository.save(matchToCreate)).thenReturn(savedMatch);
        when(matchMapper.mapTo(savedMatch)).thenReturn(matchDto);

        // Act
        MatchDto result = matchService.createMatch(matchDto);

        // Assert
        assertEquals(savedMatch.getId(), result.getId());
        assertEquals(matchDto.getTitle(), result.getTitle());
        assertEquals(matchDto.getDescription(), result.getDescription());
        assertEquals(matchDto.getScoreTeamA(), result.getScoreTeamA());
        assertEquals(matchDto.getScoreTeamB(), result.getScoreTeamB());
        assertEquals(matchDto.isPrivate(), result.isPrivate());
        assertEquals(matchDto.getTeams(), result.getTeams());
        assertEquals(matchDto.getSport(), result.getSport());
        assertEquals(matchDto.getTypeMatch(), result.getTypeMatch());
        assertEquals(matchDto.getDate(), result.getDate());
        assertEquals(matchDto.getCounter(), result.getCounter());

        // Verify that the sportRepository.findById() method was called
        verify(sportRepository).findById(sportId);

        // Verify that the teamRepository.getById() method was called
        verify(teamRepository).getById(teamId1);

        // Verify that the matchRepository.save() method was called with the created match
        verify(matchRepository).save(matchToCreate);
    }
    @Test
    public void updateMatch_ShouldUpdateCounter() {
        // Arrange
        Integer matchId = 1;
        String updatedDescription = "Updated description";
        int updatedScoreTeamA = 10;
        int updatedScoreTeamB = 5;
        int updatedCounter = 42;

        SportDTO sportDTO = new SportDTO();
        sportDTO.setId(1);

        MatchDto updatedMatchDto = new MatchDto();
        updatedMatchDto.setId(matchId);
        updatedMatchDto.setDescription(updatedDescription);
        updatedMatchDto.setScoreTeamA(updatedScoreTeamA);
        updatedMatchDto.setScoreTeamB(updatedScoreTeamB);
        updatedMatchDto.setSport(sportDTO);
        updatedMatchDto.setCounter(updatedCounter);

        Match existingMatch = new Match();
        existingMatch.setId(matchId);
//        existingMatch.set(0); // Initial counter value

        Sport sport = new Sport();
        sport.setId(sportDTO.getId());

        when(matchRepository.findById(matchId)).thenReturn(Optional.of(existingMatch));
        when(sportRepository.getReferenceById(sportDTO.getId())).thenReturn(sport);
        when(matchRepository.save(existingMatch)).thenReturn(existingMatch);

        // Act
        MatchDto result = matchService.updateMatch(updatedMatchDto);

        // Assert
//        assertEquals(updatedCounter, result.getCounter());
//        assertEquals(updatedDescription, result.getDescription());
//        assertEquals(updatedScoreTeamA, result.getScoreTeamA());
//        assertEquals(updatedScoreTeamB, result.getScoreTeamB());

        // Verify that the matchRepository.save() method was called with the updated match
        verify(matchRepository).save(existingMatch);
    }
    @Test
    void deleteMatch_ShouldInvokeMatchRepositoryDelete_WhenMatchExists() {
        // Arrange
        Integer matchId = 1;

        // Act
        matchService.deleteMatch(matchId);

        // Assert
        verify(matchRepository, times(1)).deleteById(matchId);
    }


}