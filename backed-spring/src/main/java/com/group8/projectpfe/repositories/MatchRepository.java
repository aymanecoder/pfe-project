package com.group8.projectpfe.repositories;

import com.group8.projectpfe.entities.Match;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match,Integer> {
    @Query("SELECT DISTINCT m FROM Match m LEFT JOIN FETCH m.teams")
    List<Match> findAllWithTeams();

}
