package com.group8.projectpfe.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.group8.projectpfe.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;

}
