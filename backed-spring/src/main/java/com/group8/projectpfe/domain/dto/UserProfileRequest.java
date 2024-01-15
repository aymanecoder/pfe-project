package com.group8.projectpfe.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private Integer age;
    private Integer taille;
    private Integer poids;
    private String PicturePath;
}
