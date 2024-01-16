package com.group8.projectpfe.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.group8.projectpfe.entities.Team;
import com.group8.projectpfe.repositories.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @Test
    void findByDescription_WhenDescriptionExists_ReturnsMatchingTeams() {
        // Arrange
        Team team1 = Team.builder().description("Test Team 1").build();
        Team team2 = Team.builder().description("Test Team 2").build();

        teamRepository.saveAll(List.of(team1, team2));

        List<Team> foundTeams = teamRepository.findByDescription("Test Team 1");
        assertNotNull(foundTeams);
        assertEquals(1, foundTeams.size());
        assertEquals("Test Team 1", foundTeams.get(0).getDescription());
    }

    @Test
    void findByDescription_WhenDescriptionDoesNotExist_ReturnsEmptyList() {
        // Arrange
        Team team = Team.builder().description("Test Team").build();
        teamRepository.save(team);

        // Act
        List<Team> foundTeams = teamRepository.findByDescription("Nonexistent Team");

        // Assert
        assertNotNull(foundTeams);
        assertTrue(foundTeams.isEmpty());
    }
}
