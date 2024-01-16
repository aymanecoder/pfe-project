package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.CoachDTO;
import com.group8.projectpfe.entities.Role;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.mappers.impl.CoachMapper;
import com.group8.projectpfe.repositories.UserRepository;
import com.group8.projectpfe.services.Impl.CoachServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CoachServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CoachMapper coachMapper;

    @InjectMocks
    private CoachServiceImpl coachService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCoachs() {
        // Mock the UserRepository to return a list of User entities with Role.COACH
        List<User> users = new ArrayList<>();
        users.add(User.builder().firstName("John").lastName("Doe").role(Role.COACH).build());
        users.add(User.builder().firstName("Jane").lastName("Smith").role(Role.COACH).build());
        when(userRepository.findByRole(Role.COACH)).thenReturn(users);

        // Mock the CoachMapper to return a list of CoachDTOs
        List<CoachDTO> coachDTOs = new ArrayList<>();
        coachDTOs.add(CoachDTO.builder().firstName("John").lastName("Doe").build());
        coachDTOs.add(CoachDTO.builder().firstName("Jane").lastName("Smith").build());
        when(coachMapper.mapTo(any(User.class))).thenReturn(new CoachDTO());

        // Call the service method
        List<CoachDTO> result = coachService.getCoachs();

        // Verify the UserRepository findByRole method is called once
        verify(userRepository, times(1)).findByRole(Role.COACH);

        // Verify the CoachMapper mapTo method is called for each user
        verify(coachMapper, times(users.size())).mapTo(any(User.class));

        // Assert the result
        assertEquals(users.size(), result.size());
    }

    // Add more test methods for other service methods (e.g., getCoachById, deleteCoach, updateCoach)
}