package com.group8.projectpfe.domain.dto;

import com.group8.projectpfe.domain.dto.SportDTO;
import com.group8.projectpfe.domain.dto.TeamDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeDTO {

    private int id;
    private List<TeamDTO> teams;
    private SportDTO sport;
    private String logoPath;
    private String title;
    private String description;
    private int nbrTeams;
    private String creationDate;


}
