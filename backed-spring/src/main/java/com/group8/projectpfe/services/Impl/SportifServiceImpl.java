package com.group8.projectpfe.services.Impl;

import com.group8.projectpfe.domain.dto.SportifDTO;
import com.group8.projectpfe.entities.Role;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.repositories.UserRepository;
import com.group8.projectpfe.services.SportifService;
import lombok.RequiredArgsConstructor;
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
    @Override
    public List<SportifDTO> getSportifs() {
        List<User> users=userRepository.findByRole(Role.USER);
        return users.stream()
                .map(sportifMapper::mapTo)
                .collect(Collectors.toList());
    }
    @Override
    public SportifDTO getSportifById(Long sportifId) {
        Optional<User> sportif = userRepository.findByIdAndRole(sportifId, Role.USER);
        return sportif.map(sportifMapper::mapTo).orElse(null);
    }
}
