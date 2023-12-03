package com.group8.projectpfe.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {

    private int id;
    private int adminId;
    private List<SportifDTO> members;
    private String logo;
    private String description;



}

