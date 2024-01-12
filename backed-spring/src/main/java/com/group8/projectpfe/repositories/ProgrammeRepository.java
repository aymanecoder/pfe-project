package com.group8.projectpfe.repositories;

import com.group8.projectpfe.entities.Programme;
import com.group8.projectpfe.entities.TypeProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgrammeRepository extends JpaRepository<Programme, Long> {
    List<Programme> findByTitle(String title);
    List<Programme> findByTypeProgramme(TypeProgram typeProgramme);



}
