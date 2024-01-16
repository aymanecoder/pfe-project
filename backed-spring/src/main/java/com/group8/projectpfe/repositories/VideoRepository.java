package com.group8.projectpfe.repositories;

import com.group8.projectpfe.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video,Integer> {
    Optional<Video> findById(@Param("id") Integer id);

}
