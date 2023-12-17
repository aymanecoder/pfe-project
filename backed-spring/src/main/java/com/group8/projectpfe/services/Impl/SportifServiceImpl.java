package com.group8.projectpfe.services.Impl;

import com.group8.projectpfe.domain.dto.SportifDTO;
import com.group8.projectpfe.entities.Role;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.repositories.UserRepository;
import com.group8.projectpfe.services.SportifService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.group8.projectpfe.mappers.impl.SportifMapper;
@Service
@RequiredArgsConstructor
public class SportifServiceImpl implements SportifService {
    private final UserRepository userRepository;
    private final SportifMapper sportifMapper;
    private final ModelMapper modelMapper;
    @Override
    @Transactional
    public List<SportifDTO> getSportifs() {
        List<User> users=userRepository.findByRole(Role.USER);
        return users.stream()
                .map(sportifMapper::mapTo)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional
    public SportifDTO getSportifById(Long sportifId) {
        Optional<User> sportif = userRepository.findByIdAndRole(sportifId, Role.USER);
        return sportif.map(sportifMapper::mapTo).orElse(null);
    }

    @Override
    public void deleteSportif(Integer id, Integer userId) {
        // Fetch the Sportif by ID
        User sportif = userRepository.findByIdAndRole(Long.valueOf(id),Role.USER).orElse(null);
        if (sportif != null && sportif.getId().equals(userId)) {
            userRepository.delete(sportif); // Or perform any deletion logic
        } else {
            throw new IllegalStateException("Unauthorized deletion or sportif not found");
        }
    }

    @Override
    public void updateSportif(SportifDTO sportifDTO) {
        User existingSportif = userRepository.findByIdAndRole(Long.valueOf(sportifDTO.getId()),Role.USER).orElse(null);
        if (existingSportif != null) {
            modelMapper.map(sportifDTO, existingSportif);
            userRepository.save(existingSportif);
        } else {
            throw new EntityNotFoundException("Sportif not found with ID: " + sportifDTO.getId());
        }
    }
}
