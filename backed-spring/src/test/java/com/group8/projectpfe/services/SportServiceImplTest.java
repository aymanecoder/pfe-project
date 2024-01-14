package com.group8.projectpfe.services;
import com.group8.projectpfe.domain.dto.SportDTO;
import com.group8.projectpfe.entities.Sport;
import com.group8.projectpfe.mappers.impl.SportMapperImpl;
import com.group8.projectpfe.repositories.SportRepository;
import com.group8.projectpfe.services.Impl.SportServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SportServiceImplTest {

    private SportServiceImpl sportService;

    @Mock
    private SportRepository sportRepository;

    private SportMapperImpl sportMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        ModelMapper modelMapper = Mockito.mock(ModelMapper.class);
        sportMapper = new SportMapperImpl(modelMapper);

        sportService = new SportServiceImpl(sportRepository, sportMapper, modelMapper);
    }



    @Test
    public void getSportById_NonExistingId_ShouldReturnEmptyOptional() {
        // Arrange
        int id = 1;
        when(sportRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<SportDTO> result = sportService.getSportById(id);

        // Assert
        assertEquals(Optional.empty(), result);
    }

    @Test
    public void createSport_ValidSportDTO_ShouldCreateSport() {
        // Arrange
        SportDTO sportDTO = new SportDTO();
        sportDTO.setName("Football");

        Sport sportToCreate = sportMapper.mapFrom(sportDTO);
        Sport savedSport = Sport.builder()
                .id(1)
                .name("Football")
                .build();

        when(sportRepository.save(sportToCreate)).thenReturn(savedSport);

        // Act
        SportDTO result = sportService.createSport(sportDTO);

        // Assert
//        assertEquals("Football", result.getName());
    }

    @Test
    public void updateSport_ExistingIdAndValidSportDTO_ShouldUpdateSport() {
        // Arrange
        int id = 1;
        Sport existingSport = Sport.builder()
                .id(id)
                .name("Football")
                .build();
        SportDTO sportDTO = new SportDTO();
        sportDTO.setId(id);
        sportDTO.setName("Basketball");

        when(sportRepository.findById(id)).thenReturn(Optional.of(existingSport));
        when(sportRepository.save(existingSport)).thenReturn(existingSport);

        // Act
        SportDTO result = sportService.updateSport(id, sportDTO);

        // Assert
        assertEquals("Basketball", result.getName());
    }

    @Test
    public void updateSport_NonExistingId_ShouldThrowEntityNotFoundException() {
        // Arrange
        int id = 1;
        SportDTO sportDTO = new SportDTO();
        sportDTO.setId(id);
        sportDTO.setName("Basketball");

        when(sportRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class,
                () -> sportService.updateSport(id, sportDTO));
    }

    @Test
    public void deleteSport_ExistingId_ShouldDeleteSport() {
        // Arrange
        int id = 1;

        // Act
        sportService.deleteSport(id);

        // Assert
        verify(sportRepository, times(1)).deleteById(id);
    }
}