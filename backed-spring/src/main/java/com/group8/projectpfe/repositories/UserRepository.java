package com.group8.projectpfe.repositories;

import com.group8.projectpfe.entities.Role;
import com.group8.projectpfe.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByIdAndRole(Long id,Role role);
    List<User> findByRole(Role role);

}
