package com.group8.projectpfe.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User admin;

    @OneToMany(cascade = CascadeType.ALL)
    private List<User> members;
    private String logo;
    private String description;
    @ManyToOne(cascade = CascadeType.ALL)
    private Sport sport;



}