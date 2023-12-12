package com.group8.projectpfe.domain.dto;

import com.group8.projectpfe.entities.Sport;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class EquipeDTO {
    private Integer id;
    private SportifDTO admin;
    private List<SportifDTO> members;
    private byte[] logo; // Assuming a byte array for storing the image (you can use Blob in PostgreSQL)
    private String description;
}
