package com.group8.projectpfe.services;


import com.group8.projectpfe.domain.dto.SportDTO;
import com.group8.projectpfe.entities.Sport;
import com.group8.projectpfe.mappers.impl.SportMapper;
import com.group8.projectpfe.repositories.SportRepository;
import com.group8.projectpfe.services.Impl.SportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class SportServiceTest {

    @Mock
    private SportRepository sportRepository;

    @Mock
    private SportMapper sportMapper;

    @InjectMocks
    private SportServiceImpl sportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllSports() {
        // Arrange
        Sport sport1 = new Sport();
        sport1.setId(1);
        sport1.setName("Football");

        Sport sport2 = new Sport();
        sport2.setId(2);
        sport2.setName("Basketball");

        List<Sport> sports = Arrays.asList(sport1, sport2);

        when(sportRepository.findAll()).thenReturn(sports);

        SportDTO sportDTO1 = new SportDTO();
        sportDTO1.setId(1);
        sportDTO1.setName("Football");

        SportDTO sportDTO2 = new SportDTO();
        sportDTO2.setId(2);
        sportDTO2.setName("Basketball");

        when(sportMapper.mapTo(sport1)).thenReturn(sportDTO1);
        when(sportMapper.mapTo(sport2)).thenReturn(sportDTO2);

        // Act
        List<SportDTO> result = sportService.getAllSports();

        // Assert
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("Football", result.get(0).getName());
        assertEquals(2, result.get(1).getId());
        assertEquals("Basketball", result.get(1).getName());

        verify(sportRepository, times(1)).findAll();
        verify(sportMapper, times(2)).mapTo(any());
    }

    @Test
    void getSportById() {
        // Arrange
        long sportId = 1;
        Sport sport = new Sport();
        sport.setId((int) sportId);
        sport.setName("Football");

        when(sportRepository.findById((int) sportId)).thenReturn(Optional.of(sport));

        SportDTO sportDTO = new SportDTO();
        sportDTO.setId((int) sportId);
        sportDTO.setName("Football");

        when(sportMapper.mapTo(sport)).thenReturn(sportDTO);

        // Act
        SportDTO result = sportService.getSportById((int) sportId);

        // Assert
        assertEquals(sportId, result.getId());
        assertEquals("Football", result.getName());

        verify(sportRepository, times(1)).findById((int) sportId);
        verify(sportMapper, times(1)).mapTo(sport);
    }

    @Test
    void getSportById_NotFound() {
        // Arrange
        long sportId = 1;

        when(sportRepository.findById((int) sportId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sportService.getSportById((int) sportId));

        verify(sportRepository, times(1)).findById((int) sportId);
        verifyNoInteractions(sportMapper);
    }

    @Test
    void createSport() {
        // Arrange
        SportDTO sportDTO = new SportDTO();
        sportDTO.setName("Football");

        Sport sport = new Sport();
        sport.setId(1);
        sport.setName("Football");

        when(sportMapper.mapFrom(sportDTO)).thenReturn(sport);
        when(sportRepository.save(sport)).thenReturn(sport);

        // Act
        SportDTO result = sportService.createSport(sportDTO);

        // Assert
        assertEquals(1, result.getId());
        assertEquals("Football", result.getName());

        verify(sportMapper, times(1)).mapFrom(sportDTO);
        verify(sportRepository, times(1)).save(sport);
    }

    @Test
    void updateSport() {
        // Arrange
        long sportId = 1;
        SportDTO sportDTO = new SportDTO();
        sportDTO.setId((int) sportId);
        sportDTO.setName("Football");

        Sport existingSport = new Sport();
        existingSport.setId((int) sportId);
        existingSport.setName("Soccer");

        when(sportRepository.findById((int) sportId)).thenReturn(Optional.of(existingSport));

        Sport updatedSport = new Sport();
        updatedSport.setId((int) sportId);
        updatedSport.setName("Football");

        when(sportRepository.save(updatedSport)).thenReturn(updatedSport);

        // Act
        SportDTO result = sportService.updateSport((int) sportId, sportDTO);

        // Assert
        assertEquals(sportId, result.getId());
        assertEquals("Football", result.getName());

        verify(sportRepository, times(1)).findById((int) sportId);
        verify(sportRepository, times(1)).save(updatedSport);
    }

    @Test
    void updateSport_NotFound() {
        // Arrange
        long sportId = 1;
        SportDTO sportDTO = new SportDTO();
        sportDTO.setId((int) sportId);
        sportDTO.setName("Football");

        when(sportRepository.findById((int) sportId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sportService.updateSport((int) sportId, sportDTO));

        verify(sportRepository, times(1)).findById((int) sportId);
        verifyNoInteractions(sportRepository);
    }

    @Test
    void deleteSport() {
        // Arrange
        long sportId = 1;
        Sport existingSport = new Sport();
        existingSport.setId((int) sportId);
        existingSport.setName("Football");

        when(sportRepository.findById((int) sportId)).thenReturn(Optional.of(existingSport));

        // Act
        sportService.deleteSport((int) sportId);

        // Assert
        verify(sportRepository, times(1)).findById((int) sportId);
        verify(sportRepository, times(1)).delete(existingSport);
    }

    @Test
    void deleteSport_NotFound() {
        // Arrange
        long sportId = 1;

        when(sportRepository.findById((int) sportId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sportService.deleteSport((int) sportId));

        verify(sportRepository, times(1)).findById((int) sportId);
        verifyNoInteractions(sportRepository);
    }
}

