package com.group8.projectpfe.domain.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class SportDTO {
    private Integer id;
    private String name;
    private String description;
    private String  logoPath;
}
