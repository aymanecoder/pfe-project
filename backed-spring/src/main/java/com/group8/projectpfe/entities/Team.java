package com.group8.projectpfe.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "admin_id")
    private User admin;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "team_id")
    private List<User> members;

    @Column(name = "logo")
    private String logo;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Sport sport;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

}