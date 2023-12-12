package com.group8.projectpfe.repositories;

import com.group8.projectpfe.entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {
    // You can add custom query methods here if needed
}
