package com.group8.projectpfe.domain.dto;

import com.group8.projectpfe.entities.TypeProgram;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgrammeDTO {
    private Long id;
    private String descreption;
    private String title;
    private String typeProgramme;
    private String  PicturePath;

}

