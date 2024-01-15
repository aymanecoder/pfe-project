package com.group8.projectpfe.repository;

import com.group8.projectpfe.entities.Challenge;
import com.group8.projectpfe.repositories.ChallengeRepository;
import com.group8.projectpfe.entities.Sport;
import com.group8.projectpfe.entities.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ChallengeRepositoryTest {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Test
    public void testSaveAndFindAll() {
        // Arrange
        Challenge challengeToSave = new Challenge();
        challengeToSave.setLogoPath("path/to/logo");
        Date expectedCreationDate = new Date();
        challengeToSave.setCreationDate("2023-01-12");

        // Create and set Teams and Sport
        Team team1 = new Team();
        Sport sport = new Sport();

        challengeToSave.setTeams(Collections.singletonList(team1));
        challengeToSave.setSport(sport);

        // Save Challenge
        Challenge savedChallenge = challengeRepository.save(challengeToSave);
        List<Challenge> allChallenges = challengeRepository.findAll();

        // Assert
        assertNotNull(savedChallenge.getId());
        assertEquals(1, allChallenges.size());

        Challenge foundChallenge = allChallenges.get(0);
        assertEquals(savedChallenge.getId(), foundChallenge.getId());
        assertEquals("path/to/logo", foundChallenge.getLogoPath());

        // Compare creation dates using milliseconds since epoch
//        assertEquals(expectedCreationDate.getTime(), foundChallenge.getCreationDate());

        // Additional assertions based on your entity structure
    }
}
