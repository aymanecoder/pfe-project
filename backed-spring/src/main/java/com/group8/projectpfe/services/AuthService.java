package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.AuthenticationResponse;
import com.group8.projectpfe.domain.dto.RegisterRequest;

public interface AuthService {
    AuthenticationResponse register(RegisterRequest request);
}
