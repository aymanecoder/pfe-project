package com.group8.projectpfe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String description;
    private int score;
    private boolean isPrivate;

    @OneToMany(mappedBy = "match")
    private List<Team> teams;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> participants;

    @ManyToOne
    private Sport typeDeSport;
}




