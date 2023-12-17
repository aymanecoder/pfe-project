package com.group8.projectpfe.entities;

import com.group8.projectpfe.domain.dto.ChallengeDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "sport", cascade = CascadeType.ALL)
    private List<Team> teams;

    @OneToMany(mappedBy = "sport", cascade = CascadeType.ALL)
    private List<Challenge> challenges;

}
