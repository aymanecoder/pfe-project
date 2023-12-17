package com.group8.projectpfe.domain.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VideoDto {
    private int id;
    private String titre;
    private String description;
    private String tags;
    private String videoName;
    private String addedDate;
}
