package com.group8.projectpfe.repositories;

import com.group8.projectpfe.entities.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<VideoEntity,Integer> {
}
