package com.group8.projectpfe.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {

    private int id;
    private SportifDTO admin;
    private List<SportifDTO> members;
    private String logo;
    private String description;

}

