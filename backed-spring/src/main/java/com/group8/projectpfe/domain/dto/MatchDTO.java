package com.group8.projectpfe.domain.dto;

import com.group8.projectpfe.entities.Sport;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class MatchDTO {

    private int id;
    private String description;
    private String titre;
    private List<SportifDTO> participants;
    private List<EquipeDTO> Equipes;
    private int score;
    private String status;
    private boolean isPrivate;
    private Sport typeDeSport;
}
