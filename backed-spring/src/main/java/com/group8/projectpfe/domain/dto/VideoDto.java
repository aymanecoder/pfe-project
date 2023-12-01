package com.group8.projectpfe.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class VideoDto {
    private Integer id;
    private String url;
    private String titre;
}
