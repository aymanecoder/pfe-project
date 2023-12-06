package com.group8.projectpfe.domain.dto;


import com.group8.projectpfe.entities.Sport;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeDTO {

    private int id;
    private List<TeamDTO> teams;
    private SportDTO sport;
}
