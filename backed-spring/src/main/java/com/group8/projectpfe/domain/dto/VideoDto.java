package com.group8.projectpfe.domain.dto;

import jakarta.persistence.Column;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VideoDto {
    private Integer id;
    private String titre;
    private String videoName;
    private String description;
    private int numberOfTeam;
    private String addedDate;
    private String urlVideo;
}
