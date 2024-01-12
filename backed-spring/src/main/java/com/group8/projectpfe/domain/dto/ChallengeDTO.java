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
    private Date creationDate;

    // Additional constructor
    public ChallengeDTO(int id, List<TeamDTO> teams, SportDTO sport, String logoPath,
                        String title, String description, int nbrTeams, String creationDate) {
        this.id = id;
        this.teams = teams;
        this.sport = sport;
        this.logoPath = logoPath;
        this.title = title;
        this.description = description;
        this.nbrTeams = nbrTeams;

        // Parse the string date to a Date object
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            this.creationDate = dateFormat.parse(creationDate);
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the exception according to your needs
        }
    }
}
