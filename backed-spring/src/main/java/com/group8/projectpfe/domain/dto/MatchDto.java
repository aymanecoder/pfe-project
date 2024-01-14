package com.group8.projectpfe.domain.dto;
import com.group8.projectpfe.entities.MatchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private int scoreTeamA;
    private int scoreTeamB;
    private boolean isPrivate;
    private List<TeamDTO> teams;
    private SportDTO sport;
    private MatchType typeMatch;
    private LocalDateTime date;
    private int counter;


}

