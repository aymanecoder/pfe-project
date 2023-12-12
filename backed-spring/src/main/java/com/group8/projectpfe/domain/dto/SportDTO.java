package com.group8.projectpfe.domain.dto;

import com.group8.projectpfe.entities.Match;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class SportDTO {
    private int id;
    private String description;
    private String name;
    private List<MatchDTO> matches;

}
