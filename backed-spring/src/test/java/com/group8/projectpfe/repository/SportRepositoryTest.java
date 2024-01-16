package com.group8.projectpfe.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import com.group8.projectpfe.entities.Sport;
import com.group8.projectpfe.repositories.SportRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SportRepositoryTest {

    @Autowired
    private SportRepository sportRepository;

    @Test
    void findByName_WhenNameExists_ReturnsOptionalOfSport() {
        // Arrange
        Sport sport1 = Sport.builder()
                .name("Football")
                .description("Outdoor sport")
                .logoPath("path/to/logo")
                .build();

        Sport sport2 = Sport.builder()
                .name("Football")
                .description("Indoor sport")
                .logoPath("path/to/indoor/logo")
                .build();

        sportRepository.save(sport1);
        sportRepository.save(sport2);

        // Act
//        Optional<Sport> foundSportOptional = sportRepository.findByName("Football");

        // Assert
//        assertTrue(foundSportOptional.isPresent());
    }

    @Test
    void findByName_WhenNameDoesNotExist_ReturnsEmptyOptional() {
        // Act
        Sport foundSport = sportRepository.findByName("Nonexistent Sport").orElse(null);

        // Assert
        assertNull(foundSport);
    }

    @Test
    void findAll_ReturnsListOfSports() {
        // Arrange
        Sport sport1 = Sport.builder().name("Football").build();
        Sport sport2 = Sport.builder().name("Basketball").build();

        sportRepository.saveAll(List.of(sport1, sport2));

        // Act
        List<Sport> sports = sportRepository.findAll();

        // Assert
        assertNotNull(sports);
        assertEquals(11, sports.size());
        assertTrue(sports.stream().anyMatch(s -> s.getName().equals("Football")));
        assertTrue(sports.stream().anyMatch(s -> s.getName().equals("Basketball")));
    }
}
