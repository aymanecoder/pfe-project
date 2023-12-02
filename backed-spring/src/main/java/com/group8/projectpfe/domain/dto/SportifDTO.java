package com.group8.projectpfe.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class SportifDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private Integer age;
    private Integer taille;
    private Integer poids;
    private byte[] picture;
}
