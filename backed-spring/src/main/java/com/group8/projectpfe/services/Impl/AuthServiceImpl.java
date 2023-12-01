package com.group8.projectpfe.services.Impl;

import com.group8.projectpfe.domain.dto.AuthenticationResponse;
import com.group8.projectpfe.domain.dto.RegisterRequest;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.repositories.UserRepository;
import com.group8.projectpfe.services.AuthService;
import com.group8.projectpfe.services.JwtService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

}
