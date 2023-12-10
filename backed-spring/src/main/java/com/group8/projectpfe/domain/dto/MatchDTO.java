package com.group8.projectpfe.domain.dto;

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
    private int score;
    private String status;
    private boolean isPrivate;
}
