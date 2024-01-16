package com.group8.projectpfe.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import com.group8.projectpfe.entities.Programme;
import com.group8.projectpfe.entities.TypeProgram;
import com.group8.projectpfe.repositories.ProgrammeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
public class ProgrammeRepositoryTest {

    @Autowired
    private ProgrammeRepository ProgrammeRepository;

    @Test
    public void saveProgrammeAndFindById() {
        // Arrange
        Programme ProgrammeToSave = new Programme();
        ProgrammeToSave.setDescreption("Test Description");
        ProgrammeToSave.setTitle("Test Title");
        ProgrammeToSave.setTypeProgramme(TypeProgram.ENTRAINEMENT); // Replace TEST_TYPE with your actual Enum value
        ProgrammeToSave.setPicturePath("path/to/test/picture");

        // Act
        Programme savedProgramme = ProgrammeRepository.save(ProgrammeToSave);
        Optional<Programme> foundProgrammeOptional = ProgrammeRepository.findById(savedProgramme.getId());

        // Assert
        assertTrue(foundProgrammeOptional.isPresent());
        Programme foundProgramme = foundProgrammeOptional.get();
        assertEquals(savedProgramme.getId(), foundProgramme.getId());
        assertEquals(savedProgramme.getDescreption(), foundProgramme.getDescreption());
        assertEquals(savedProgramme.getTitle(), foundProgramme.getTitle());
        assertEquals(savedProgramme.getTypeProgramme(), foundProgramme.getTypeProgramme());
        assertEquals(savedProgramme.getPicturePath(), foundProgramme.getPicturePath());
    }

//    @Test
//    public void findByDescription() {
//        // Arrange
//        Programme programme1 = new Programme();
//        programme1.setDescreption("Description1");
//        programme1.setTitle("Title1");
//        programme1.setTypeProgramme(TypeProgram.ENTRAINEMENT);
//        programme1.setPicturePath("path/to/picture1");
//        ProgrammeRepository.save(programme1);
//
//        Programme programme2 = new Programme();
//        programme2.setDescreption("Description2");
//        programme2.setTitle("Title2");
//        programme2.setTypeProgramme(TypeProgram.ENTRAINEMENT);
//        programme2.setPicturePath("path/to/picture2");
//        ProgrammeRepository.save(programme2);
//
//        // Act
//        List<Programme> foundProgrammes = ProgrammeRepository.findByDescription("Description1");
//
//        // Assert
//        assertEquals(2, foundProgrammes.size());
//        assertEquals("Description1", foundProgrammes.get(0).getDescreption());
//    }

}
