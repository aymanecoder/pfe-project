package com.group8.projectpfe.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Equipes")
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne // Assuming a Many-to-One relationship with the User entity
    private User admin;

    @ManyToMany(mappedBy = "Equipes")
    private List<Match> matches;

    @ManyToMany(mappedBy = "equipes")
    private List<User> members;

    @Lob // Use @Lob for large objects like byte arrays (e.g., images)
    private byte[] logo;
    private String description;

}
