package com.group8.projectpfe.repository;

import com.group8.projectpfe.entities.Group;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.repositories.GroupRepository;
import com.group8.projectpfe.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveGroupAndFindById() {
        // Arrange
        Group groupToSave = Group.builder()
                .name("Test Group")
                .build();

        // Save the Group
        Group savedGroup = groupRepository.save(groupToSave);

        // Act
        Optional<Group> foundGroupOptional = groupRepository.findById(savedGroup.getId());

        // Assert
        assertTrue(foundGroupOptional.isPresent());
        Group foundGroup = foundGroupOptional.get();
        assertEquals(savedGroup.getId(), foundGroup.getId());
        assertEquals("Test Group", foundGroup.getName());

        // Add more assertions based on your entity structure

        // Clean up
        groupRepository.delete(foundGroup);
    }

    @Test
    public void findAllGroups() {
        // Arrange
        Group group1 = Group.builder().name("Group 1").build();
        Group group2 = Group.builder().name("Group 2").build();

        // Save Groups
        groupRepository.saveAll(List.of(group1, group2));

        // Act
        List<Group> allGroups = groupRepository.findAll();

        // Assert
        assertNotNull(allGroups);
        assertEquals(2, allGroups.size());
        // Add more assertions based on your entity structure

        // Clean up
        groupRepository.deleteAll(allGroups);
    }


}

