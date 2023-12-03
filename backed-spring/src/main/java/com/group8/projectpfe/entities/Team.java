package com.group8.projectpfe.entities;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="TEAMS")
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Team implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private int id;

    @Column(name = "admin_id")
    private int adminId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    //private List<Sportif> members;

    @Column(name = "logo")
    private String logo;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

}