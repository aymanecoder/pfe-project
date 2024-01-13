package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.MatchDto;
import com.group8.projectpfe.domain.dto.SportDTO;
import com.group8.projectpfe.domain.dto.SportifDTO;
import com.group8.projectpfe.domain.dto.TeamDTO;
import com.group8.projectpfe.entities.Match;
import com.group8.projectpfe.entities.Sport;
import com.group8.projectpfe.entities.Team;
import com.group8.projectpfe.entities.User;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Assertions.assertEquals(expectedMatchDto, result);
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
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(matchDto1, result.get(0));
        Assertions.assertEquals(matchDto2, result.get(1));
        verify(matchRepository, times(1)).findAll();
//        verify(matchMapper, times(1)).mapTo(matches.get(0));
//        verify(matchMapper, times(1)).mapTo(matches.get(1));
    }

    @Test
    void createMatch_ShouldReturnCreatedMatchDto() {
        // Arrange
        MatchDto matchDto = new MatchDto();
        Match matchToCreate = new Match();
        Match savedMatch = new Match();
        MatchDto expectedMatchDto = new MatchDto();

        // Initialize the sport property
        Sport sport = new Sport();
        sport.setId(1); // Set the ID or any other necessary properties
        matchToCreate.setSport(sport);

        // Initialize the teams and participants properties
        matchToCreate.setTeams(new ArrayList<>());
        matchDto.setParticipants(new ArrayList<>());

        when(matchMapper.mapFrom(matchDto)).thenReturn(matchToCreate);
//        when(teamRepository.getById(anyInt())).thenReturn(new Team());
//        when(userRepository.getById(anyInt())).thenReturn(new User());
        when(sportRepository.findById(anyInt())).thenReturn(Optional.of(sport));
        when(matchRepository.save(matchToCreate)).thenReturn(savedMatch);
        when(matchMapper.mapTo(savedMatch)).thenReturn(expectedMatchDto);

        // Act
        MatchDto result = matchService.createMatch(matchDto);

        // Assert
        Assertions.assertEquals(expectedMatchDto, result);
        verify(matchMapper, times(1)).mapFrom(matchDto);
//        verify(teamRepository, times(1)).getById(anyInt());
//        verify(userRepository, times(1)).getById(anyInt());
        verify(sportRepository, times(1)).findById(anyInt());
        verify(matchRepository, times(1)).save(matchToCreate);
        verify(matchMapper, times(1)).mapTo(savedMatch);
    }
    @Test
    void updateMatch_ShouldReturnUpdatedMatchDto_WhenMatchExists() {
        // Arrange
        MatchDto updatedMatchDto = new MatchDto();
        updatedMatchDto.setId(1);

        SportDTO sportDto = new SportDTO();
        sportDto.setId(1); // Set the ID or any other necessary properties
        updatedMatchDto.setSport(sportDto);

        List<SportifDTO> participants = new ArrayList<>(); // Initialize the participants list
        updatedMatchDto.setParticipants(participants);

        List<TeamDTO> teams = new ArrayList<>(); // Initialize the teams list
        updatedMatchDto.setTeams(teams);

        Match existingMatch = new Match();
        existingMatch.setId(1);

        Sport existingSport = new Sport();
        existingSport.setId(1);

        when(matchRepository.findById(updatedMatchDto.getId())).thenReturn(Optional.of(existingMatch));
        when(sportRepository.getReferenceById(updatedMatchDto.getSport().getId())).thenReturn(existingSport);
        when(userRepository.findAllById(anyList())).thenReturn(new ArrayList<>());
        when(teamRepository.findAllById(anyList())).thenReturn(new ArrayList<>());
        when(matchRepository.save(existingMatch)).thenReturn(existingMatch);
        when(matchMapper.mapTo(existingMatch)).thenReturn(updatedMatchDto);

        // Act
        MatchDto result = matchService.updateMatch(updatedMatchDto);

        // Assert
        Assertions.assertEquals(updatedMatchDto, result);
        verify(matchRepository, times(1)).findById(updatedMatchDto.getId());
        verify(sportRepository, times(1)).getReferenceById(updatedMatchDto.getSport().getId());
        verify(userRepository, times(1)).findAllById(anyList());
        verify(teamRepository, times(1)).findAllById(anyList());
        verify(matchRepository, times(1)).save(existingMatch);
        verify(matchMapper, times(1)).mapTo(existingMatch);
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