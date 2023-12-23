package com.group8.projectpfe.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchDto {

    private Integer id;
    private String title;
    private String description;
    private int score;
    private boolean isPrivate;
    private List<TeamDTO> teams;
    private List<SportifDTO> participants;
    private SportDTO sport;


}

