package com.group8.projectpfe.entities;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="PROGRAMMES")
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Programme implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="URL")
    private String url;
    @Column(name="TITTLE")
    private String title;
    @Column(name="FICHIER")
    private String fichier;
}


// getters and setters

