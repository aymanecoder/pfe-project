package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.SportDTO;
import com.group8.projectpfe.domain.dto.SportifDTO;
import com.group8.projectpfe.domain.dto.TeamDTO;
import com.group8.projectpfe.entities.Team;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.mappers.impl.SportifMapper;
import com.group8.projectpfe.mappers.impl.TeamMapperImpl;
import com.group8.projectpfe.repositories.SportRepository;
import com.group8.projectpfe.repositories.TeamRepository;
import com.group8.projectpfe.repositories.UserRepository;
import com.group8.projectpfe.services.Impl.TeamServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.file.attribute.UserPrincipal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TeamServiceImplTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private SportRepository sportRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SportifMapper sportifMapper;

    @Mock
    private TeamMapperImpl teamMapper;

    @InjectMocks
    private TeamServiceImpl teamService;


    @Test
    public void getTeamById_NonExistingId_ShouldReturnEmptyOptional() {
        // Arrange
        int id = 1;
        when(teamRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<TeamDTO> result = teamService.getTeamById(id);

        // Assert
        assertFalse(result.isPresent());
    }


    @Test
    public void deleteTeam_ExistingId_ShouldDeleteTeam() {
        // Arrange
        int id = 1;
        doNothing().when(teamRepository).deleteById(id);

        // Act
        teamService.deleteTeam(id);

        // Assert
        verify(teamRepository, times(1)).deleteById(id);
    }

    @Test
    public void searchByDescription_ExistingDescription_ShouldReturnMatchingTeams() {
        // Arrange
        String description = "Test";
        List<Team> teamsByDescription = Arrays.asList(
                Team.builder().id(1).name("Team 1").description("Test Description").build(),
                Team.builder().id(2).name("Team 2").description("Another Test").build()
        );
        when(teamRepository.findByDescription(description)).thenReturn(teamsByDescription);

        // Act
        List<TeamDTO> result = teamService.searchByDescriptionForTest(description);

        // Logging
        System.out.println("Number of teams returned: " + result.size());
        System.out.println("First team name: " + (result.get(0) != null ? result.get(0).getName() : "null"));
        System.out.println("Second team name: " + (result.get(1) != null ? result.get(1).getName() : "null"));

        // Assert
        assertEquals(2, result.size());
        assertEquals("Team 1", result.get(0).getName());
        assertEquals("Team 2", result.get(1).getName());
    }



}