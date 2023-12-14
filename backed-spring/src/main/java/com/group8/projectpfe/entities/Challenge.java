package com.group8.projectpfe.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Challenge{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Equipe> equipes;
    @OneToOne
    private Sport sport;
}
