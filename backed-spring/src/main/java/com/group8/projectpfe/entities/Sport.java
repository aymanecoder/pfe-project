package com.group8.projectpfe.entities;

import com.group8.projectpfe.domain.dto.ChallengeDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String description;
    @Column(length = 500)
    private String  logoPath;
    @OneToMany(mappedBy = "sport", cascade = CascadeType.ALL)
    private List<Team> teams;

    @OneToMany(mappedBy = "sport", cascade = CascadeType.ALL)
    private List<Match> matches;

    @OneToMany(mappedBy = "sport", cascade = CascadeType.ALL)
    private List<Challenge> challenges;


    public Sport(String football, String descriptionOfFootball, String image) {
        this.name = football;
        this.description = descriptionOfFootball;
        this.logoPath = image;
    }
}
