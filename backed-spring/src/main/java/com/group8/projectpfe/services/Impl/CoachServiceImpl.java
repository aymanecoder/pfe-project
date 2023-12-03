package com.group8.projectpfe.services.Impl;

import com.group8.projectpfe.domain.dto.CoachDTO;
import com.group8.projectpfe.entities.Role;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.mappers.impl.CoachMapper;
import com.group8.projectpfe.repositories.UserRepository;
import com.group8.projectpfe.services.CoachService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoachServiceImpl implements CoachService {
    private final UserRepository userRepository;
    private final CoachMapper coachMapper;
    private final ModelMapper modelMapper;
    @Override
    public List<CoachDTO> getCoachs() {
        List<User> users=userRepository.findByRole(Role.COACH);
        return users.stream()
                .map(coachMapper::mapTo)
                .collect(Collectors.toList());
    }
    @Override
    public CoachDTO getCoachById(Long coachId) {
        Optional<User> coach = userRepository.findByIdAndRole(coachId, Role.COACH);
        return coach.map(coachMapper::mapTo).orElse(null);
    }

    @Override
    public void deleteCoach(Integer id, Integer userId) {
        // Fetch the Coach by ID
        User coach = userRepository.findByIdAndRole(Long.valueOf(id),Role.COACH).orElse(null);

        if (coach != null && coach.getId().equals(userId)) {
            // Check if the user associated with the Coach matches the authenticated user
            userRepository.delete(coach); // Or perform any deletion logic
        } else {
            // Handle unauthorized deletion or coach not found
            throw new IllegalStateException("Unauthorized deletion or coach not found");
        }
    }

    @Override
    public void updateCoach(CoachDTO coachDTO) {
        User existingCoach = userRepository.findByIdAndRole(Long.valueOf(coachDTO.getId()),Role.COACH).orElse(null);

        if (existingCoach != null) {
            // Map the updated data from DTO to the existing coach entity
            //existingCoach=coachMapper.mapFrom(coachDTO);
            modelMapper.map(coachDTO, existingCoach);
            // Save the updated coach entity
            userRepository.save(existingCoach);
        } else {
            // Handle cases where the coach to be updated is not found
            throw new EntityNotFoundException("Coach not found with ID: " + coachDTO.getId());
        }
    }
}
