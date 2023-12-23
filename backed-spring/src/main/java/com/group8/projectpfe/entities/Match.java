package com.group8.projectpfe.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Match {
    @Id
    private Integer id;
    private String titre;
    private String description;
    private int score;
    @OneToMany
    private List<Team> teams;

}
