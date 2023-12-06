package com.group8.projectpfe.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SportDTO {
    private String name;
    private String description;
}
