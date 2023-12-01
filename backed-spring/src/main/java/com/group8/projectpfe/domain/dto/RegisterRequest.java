package com.group8.projectpfe.domain.dto;

import com.group8.projectpfe.entities.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;

}
