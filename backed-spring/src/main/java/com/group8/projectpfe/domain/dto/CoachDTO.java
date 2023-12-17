package com.group8.projectpfe.domain.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CoachDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private Integer age;
    private Integer taille;
    private Integer poids;
    private String PicturePath;

}

