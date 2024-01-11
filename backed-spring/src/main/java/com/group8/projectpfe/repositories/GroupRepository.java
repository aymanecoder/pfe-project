package com.group8.projectpfe.repositories;

import com.group8.projectpfe.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;

// GroupRepository.java
public interface GroupRepository extends JpaRepository<Group, Long> {
    // Custom queries if needed
}
