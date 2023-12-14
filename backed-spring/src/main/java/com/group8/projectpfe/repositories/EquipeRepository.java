package com.group8.projectpfe.repositories;


import com.group8.projectpfe.entities.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Integer> {
    List<Equipe> findByDescription(String description);

}
