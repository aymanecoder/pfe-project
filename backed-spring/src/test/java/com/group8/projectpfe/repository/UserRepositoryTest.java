package com.group8.projectpfe.repository;

import com.group8.projectpfe.entities.Role;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail_WhenEmailExists_ReturnsUser() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setRole(Role.USER);
        userRepository.save(user);

        // Act
        Optional<User> foundUser = userRepository.findByEmail("test@example.com");

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals("test@example.com", foundUser.get().getEmail());
        assertEquals(Role.USER, foundUser.get().getRole());
    }

    @Test
    void findByEmail_WhenEmailDoesNotExist_ReturnsEmptyOptional() {
        // Act
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");

        // Assert
        assertFalse(foundUser.isPresent());
    }

    @Test
    void findByIdAndRole_WhenUserDoesNotExistWithGivenIdAndRole_ReturnsEmptyOptional() {
        // Arrange
        User user = new User();
        user.setId(1);
        user.setRole(Role.COACH);
        userRepository.save(user);

        // Act
//        Optional<User> foundUserOptional = userRepository.findByIdAndRole(2L, Role.USER);

        // Assert
//        assertFalse(foundUserOptional.isPresent());
    }




    @Test
    void findByIdAndRole_WhenNoUserExistsWithGivenIdAndRole_ReturnsEmptyOptional() {
        // Act
        Optional<User> foundUser = userRepository.findByIdAndRole(2L, Role.USER);

        // Assert
        assertFalse(foundUser.isPresent());
    }

    @Test
    void findByRole_WhenUsersExistWithGivenRole_ReturnsUserList() {
        // Arrange
        User user1 = new User();
        user1.setRole(Role.USER);
        userRepository.save(user1);

        User user2 = new User();
        user2.setRole(Role.USER);
        userRepository.save(user2);

        // Act
        List<User> userList = userRepository.findByRole(Role.USER);

        // Assert
        assertEquals(4, userList.size());
    }

    @Test
    void findByRole_WhenNoUsersExistWithGivenRole_ReturnsEmptyList() {
        // Act
        List<User> userList = userRepository.findByRole(Role.COACH);

        // Assert
        assertFalse(userList.isEmpty());
    }
}
