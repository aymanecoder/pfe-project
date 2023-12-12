package com.group8.projectpfe.domain.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class SportifDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private Integer age;
    private Integer taille;
    private Integer poids;
    private byte[] picture;
    private List<MatchDTO> matches;
    private List<EquipeDTO> equipes;
}
