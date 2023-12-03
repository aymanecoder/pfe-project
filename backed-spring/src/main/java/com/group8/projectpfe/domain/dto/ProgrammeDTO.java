package com.group8.projectpfe.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgrammeDTO {
    private Long id;
    private String url;
    private String title;
    private String fichier;
    // You can add more fields if needed
}

