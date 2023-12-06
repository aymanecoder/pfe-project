package com.group8.projectpfe.domain.dto;


import com.group8.projectpfe.entities.Sport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {

    private SportifDTO admin;
    private List<SportifDTO> members;
    private String logo;
    private String description;
    private Sport sport;

}

