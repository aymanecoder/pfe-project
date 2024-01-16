package com.group8.projectpfe.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Programme{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descreption;
    private String title;
    @Enumerated(EnumType.STRING)
    private TypeProgram typeProgramme;
    private String  PicturePath;
}




