package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.ProgrammeDTO;
import com.group8.projectpfe.entities.Programme;
import com.group8.projectpfe.entities.TypeProgram;
import com.group8.projectpfe.mappers.impl.ProgrammeMapperImpl;
import com.group8.projectpfe.repositories.ProgrammeRepository;
import com.group8.projectpfe.services.Impl.ProgrammeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ProgrammeServiceImplTest {

    @Mock
    private ProgrammeRepository programmeRepository;

    @Mock
    private ProgrammeMapperImpl programmeMapper;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProgrammeServiceImpl programmeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllProgrammes_ShouldReturnListOfProgrammeDTOs() {
        // Arrange
        List<Programme> programmes = new ArrayList<>();
        programmes.add(new Programme());
        programmes.add(new Programme());
        when(programmeRepository.findAll()).thenReturn(programmes);

        List<ProgrammeDTO> expectedDTOs = new ArrayList<>();
        expectedDTOs.add(new ProgrammeDTO());
        expectedDTOs.add(new ProgrammeDTO());
        when(programmeMapper.mapTo(any(Programme.class))).thenReturn(new ProgrammeDTO());

        // Act
        List<ProgrammeDTO> result = programmeService.getAllProgrammes();

        // Assert
        Assertions.assertEquals(expectedDTOs.size(), result.size());
        // Add more assertions if necessary
    }

    @Test
    public void getProgrammeById_ExistingId_ShouldReturnProgrammeDTO() {
        // Arrange
        Long id = 1L;
        Programme programme = new Programme();
        programme.setId(id);
        Optional<Programme> programmeOptional = Optional.of(programme);
        when(programmeRepository.findById(id)).thenReturn(programmeOptional);

        ProgrammeDTO expectedDTO = new ProgrammeDTO();
        expectedDTO.setId(id);
        when(programmeMapper.mapTo(programme)).thenReturn(expectedDTO);

        // Act
        Optional<ProgrammeDTO> result = programmeService.getProgrammeById(id);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(expectedDTO.getId(), result.get().getId());
        // Add more assertions if necessary
    }

    @Test
    public void getProgrammeById_NonExistingId_ShouldReturnEmptyOptional() {
        // Arrange
        Long id = 1L;
        when(programmeRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<ProgrammeDTO> result = programmeService.getProgrammeById(id);

        // Assert
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void createProgramme_ShouldReturnCreatedProgrammeDTO() {
        // Arrange
        ProgrammeDTO programmeDetails = new ProgrammeDTO();
        Programme programmeToCreate = new Programme();
        when(modelMapper.map(programmeDetails, Programme.class)).thenReturn(programmeToCreate);

        Programme savedProgramme = new Programme();
        when(programmeRepository.save(programmeToCreate)).thenReturn(savedProgramme);

        ProgrammeDTO expectedDTO = new ProgrammeDTO();
        when(modelMapper.map(savedProgramme, ProgrammeDTO.class)).thenReturn(expectedDTO);

        // Act
        ProgrammeDTO result = programmeService.createProgramme(programmeDetails);

        // Assert
        Assertions.assertEquals(expectedDTO, result);
        // Add more assertions if necessary
    }

    @Test
    public void updateProgramme_ExistingId_ShouldReturnUpdatedProgrammeDTO() {
        // Arrange
        Long id = 1L;
        ProgrammeDTO updatedProgrammeDetails = new ProgrammeDTO();

        Programme existingProgramme = new Programme();
        Optional<Programme> optionalProgramme = Optional.of(existingProgramme);
        when(programmeRepository.findById(id)).thenReturn(optionalProgramme);

        Programme updatedProgramme = new Programme();
        when(programmeRepository.save(existingProgramme)).thenReturn(updatedProgramme);

        ProgrammeDTO expectedDTO = new ProgrammeDTO();
        when(programmeMapper.mapTo(updatedProgramme)).thenReturn(expectedDTO);

        // Act
        ProgrammeDTO result = programmeService.updateProgramme(id, updatedProgrammeDetails);

        // Assert
        Assertions.assertEquals(expectedDTO, result);
        // Add more assertions if necessary
    }

    @Test
    public void updateProgramme_NonExistingId_ShouldReturnNull() {
        // Arrange
        Long id = 1L;
        when(programmeRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        ProgrammeDTO result = programmeService.updateProgramme(id, new ProgrammeDTO());

        // Assert
        Assertions.assertNull(result);
    }

    @Test
    public void deleteProgramme_ShouldInvokeProgrammeRepositoryDeleteById() {
        // Arrange
        Long id = 1L;

        // Act
        programmeService.deleteProgramme(id);

        // Assert
        verify(programmeRepository, times(1)).deleteById(id);
    }

    @Test
    public void searchByTitle_ShouldReturnListOfProgrammeDTOs() {
        // Arrange
        String title = "Programme Title";
        List<Programme> programmesByTitle = new ArrayList<>();
        programmesByTitle.add(new Programme());
        when(programmeRepository.findByTitle(title)).thenReturn(programmesByTitle);

        List<ProgrammeDTO> expectedDTOs = new ArrayList<>();
        expectedDTOs.add(new ProgrammeDTO());
        when(programmeMapper.mapTo(any(Programme.class))).thenReturn(new ProgrammeDTO());

        // Act
        List<ProgrammeDTO> result = programmeService.searchByTitle(title);

        // Assert
        Assertions.assertEquals(expectedDTOs.size(), result.size());
        // Add more assertions if necessary
    }

}