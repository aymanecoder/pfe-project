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
    @Column(name = "team_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private List<User> members;

    @Column(name = "logo")
    private String logo;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @OneToOne
    private Sport sport;


}