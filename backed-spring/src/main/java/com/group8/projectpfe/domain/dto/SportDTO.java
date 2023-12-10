package com.group8.projectpfe.domain.dto;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class SportDTO {
    private int id;
    private String description;
    private String name;
}
