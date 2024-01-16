package com.group8.projectpfe.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamDTO {
    private Integer id;
    private String name;
    private SportifDTO admin;
    private List<SportifDTO> members;
    private String description;
    private SportDTO sport;

    private String  logoPath;
}

