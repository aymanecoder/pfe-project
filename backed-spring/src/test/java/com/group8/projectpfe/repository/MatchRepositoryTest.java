package com.group8.projectpfe.repository;
import com.group8.projectpfe.entities.Match;
import com.group8.projectpfe.entities.MatchType;
import com.group8.projectpfe.entities.Sport;
import com.group8.projectpfe.entities.Team;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.repositories.MatchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MatchRepositoryTest {

    @Autowired
    private MatchRepository matchRepository;

    @Test
    public void saveMatchAndFindById() {
        // Arrange
        Match matchToSave = new Match();
        matchToSave.setTitle("Test Match");
        matchToSave.setDescription("This is a test match");
        matchToSave.setScoreTeamA(0);
        matchToSave.setScoreTeamB(0);
        matchToSave.setPrivate(false);
        matchToSave.setSport(new Sport());
        matchToSave.setTypeMatch(MatchType.COMPLETED);
        matchToSave.setDate(LocalDateTime.now());

        // Create and set Teams and Users
        Team team1 = new Team();
        User user1 = new User();
        team1.setMembers(Collections.singletonList(user1));
        matchToSave.setTeams(Collections.singletonList(team1));
//        matchToSave.setParticipants(Collections.singletonList(user1));

        // Save the Match
        Match savedMatch = matchRepository.save(matchToSave);

        // Act
        Optional<Match> foundMatchOptional = matchRepository.findById(savedMatch.getId());

        // Assert
        assertTrue(foundMatchOptional.isPresent());
        Match foundMatch = foundMatchOptional.get();
        assertEquals(savedMatch.getId(), foundMatch.getId());
        assertEquals("Test Match", foundMatch.getTitle());
        assertEquals("This is a test match", foundMatch.getDescription());

        // Add more assertions based on your entity structure
    }

//    @Test
//    public void findAllMatches() {
//        // Arrange
//        Match match1 = new Match();
//        match1.setTitle("Match 1");
//        match1.setDescription("Description for Match 1");
//        match1.setScore(2);
//        match1.setPrivate(true);
//        match1.setSport(new Sport());
//        match1.setTypeMatch(MatchType.UPCOMING);
//        match1.setDate(LocalDateTime.now());
//
//        Match match2 = new Match();
//        match2.setTitle("Match 2");
//        match2.setDescription("Description for Match 2");
//        match2.setScore(1);
//        match2.setPrivate(false);
//        match2.setSport(new Sport());
//        match2.setTypeMatch(MatchType.COMPLETED);
//        match2.setDate(LocalDateTime.now().plusDays(1));
//
//        // Save Matches
//        matchRepository.saveAll(List.of(match1, match2));
//
//        // Act
//        List<Match> allMatches = matchRepository.findAll();
//
//        // Assert
//        assertNotNull(allMatches);
//        assertEquals(2, allMatches.size());
//        // Add more assertions based on your entity structure
//    }
}




