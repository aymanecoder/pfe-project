package com.group8.projectpfe.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Integer id;

    @ManyToOne
    private User admin;

    @OneToMany(cascade = CascadeType.ALL)
    private List<User> members;

    @Column(name = "logo")
    private String logo;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @ManyToOne(cascade = CascadeType.ALL)
    private Sport sport;


}