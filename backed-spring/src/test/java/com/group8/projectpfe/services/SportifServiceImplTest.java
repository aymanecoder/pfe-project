package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.SportifDTO;
import com.group8.projectpfe.entities.Role;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.mappers.impl.SportifMapper;
import com.group8.projectpfe.repositories.UserRepository;
import com.group8.projectpfe.services.Impl.SportifServiceImpl;
import jakarta.persistence.EntityNotFoundException;
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
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

public class SportifServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SportifMapper sportifMapper;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SportifServiceImpl sportifService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getSportifs_ShouldReturnListOfSportifDTOs() {
        // Arrange
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        when(userRepository.findByRole(Role.USER)).thenReturn(users);

        List<SportifDTO> expectedDTOs = users.stream()
                .map(user -> new SportifDTO())
                .collect(Collectors.toList());
        when(sportifMapper.mapTo(any(User.class))).thenReturn(new SportifDTO());

        // Act
        List<SportifDTO> result = sportifService.getSportifs();

        // Assert
        Assertions.assertEquals(expectedDTOs.size(), result.size());
        // Add more assertions if necessary
    }

    @Test
    public void getSportifById_ExistingId_ShouldReturnSportifDTO() {
        // Arrange
        Long sportifId = 1L;
        User sportif = new User();
        sportif.setId(Math.toIntExact(sportifId));
        Optional<User> sportifOptional = Optional.of(sportif);
        when(userRepository.findByIdAndRole(sportifId, Role.USER)).thenReturn(sportifOptional);

        SportifDTO expectedDTO = new SportifDTO();
        when(sportifMapper.mapTo(sportif)).thenReturn(expectedDTO);

        // Act
        SportifDTO result = sportifService.getSportifById(sportifId);

        // Assert
        Assertions.assertEquals(expectedDTO, result);
        // Add more assertions if necessary
    }

    @Test
    public void getSportifById_NonExistingId_ShouldReturnNull() {
        // Arrange
        Long sportifId = 1L;
        when(userRepository.findByIdAndRole(sportifId, Role.USER)).thenReturn(Optional.empty());

        // Act
        SportifDTO result = sportifService.getSportifById(sportifId);

        // Assert
        Assertions.assertNull(result);
    }



    @Test
    public void deleteSportif_IncorrectIdOrUserId_ShouldThrowException() {
        // Arrange
        Integer id = 1;
        Integer userId = 2;
        when(userRepository.findByIdAndRole(Long.valueOf(id), Role.USER)).thenReturn(Optional.empty());

        // Act and Assert
        Assertions.assertThrows(IllegalStateException.class, () -> sportifService.deleteSportif(id, userId));
    }

    @Test
    public void updateSportif_ExistingSportif_ShouldUpdateSportif() {
        // Arrange
        SportifDTO sportifDTO = new SportifDTO();
        sportifDTO.setId(1);

        User existingSportif = new User();
        existingSportif.setId((int) sportifDTO.getId());
        existingSportif.setRole(Role.USER);
        when(userRepository.findByIdAndRole(Long.valueOf(sportifDTO.getId()), Role.USER)).thenReturn(Optional.of(existingSportif));

        User savedSportif = new User();
        when(userRepository.save(existingSportif)).thenReturn(savedSportif);

        // Act
        sportifService.updateSportif(sportifDTO);

        // Assert
        verify(modelMapper, times(1)).map(sportifDTO, existingSportif);
        verify(userRepository, times(1)).save(existingSportif);
    }

    @Test
    public void updateSportif_NonExistingSportif_ShouldThrowException() {
        // Arrange
        SportifDTO sportifDTO = new SportifDTO();
        sportifDTO.setId(1);

        when(userRepository.findByIdAndRole(Long.valueOf(sportifDTO.getId()), Role.USER)).thenReturn(Optional.empty());

        // Act and Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> sportifService.updateSportif(sportifDTO));
    }
}