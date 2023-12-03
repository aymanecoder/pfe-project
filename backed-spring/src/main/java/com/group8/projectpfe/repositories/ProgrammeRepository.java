package com.group8.projectpfe.repositories;

import com.group8.projectpfe.entities.Programme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgrammeRepository extends JpaRepository<Programme, Long> {
    List<Programme> findByTitle(String title);
    // You can add custom query methods if needed
}
