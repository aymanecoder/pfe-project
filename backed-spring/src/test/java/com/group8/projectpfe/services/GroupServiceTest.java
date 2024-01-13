package com.group8.projectpfe.services;

import com.group8.projectpfe.entities.Group;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.repositories.GroupRepository;
import com.group8.projectpfe.services.Impl.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupService groupService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllGroups() {
        // Create a list of groups for the mock repository to return
        List<Group> groups = new ArrayList<>();
        groups.add(new Group(1L, "Group 1", new HashSet<>()));
        groups.add(new Group(2L, "Group 2", new HashSet<>()));

        // Mock the repository's findAll method to return the list of groups
        when(groupRepository.findAll()).thenReturn(groups);

        // Call the service method
        List<Group> result = groupService.getAllGroups();

        // Verify the result
        assertEquals(groups, result);
    }

    @Test
    public void testGetGroupById() {
        // Create a group for the mock repository to return
        Group group = new Group(1L, "Group 1", new HashSet<>());

        // Mock the repository's findById method to return the group
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        // Call the service method
        Group result = groupService.getGroupById(1L);

        // Verify the result
        assertEquals(group, result);
    }

    @Test
    public void testCreateGroup() {
        // Create a group to save
        Group groupToSave = new Group(null, "New Group", new HashSet<>());

        // Create a group with the generated ID
        Group savedGroup = new Group(1L, "New Group", new HashSet<>());

        // Mock the repository's save method to return the saved group
        when(groupRepository.save(groupToSave)).thenReturn(savedGroup);

        // Call the service method
        Group result = groupService.createGroup(groupToSave);

        // Verify the result
        assertEquals(savedGroup, result);
        assertNotNull(result.getId());
    }

    @Test
    public void testUpdateGroup() {
        // Create an existing group
        Group existingGroup = new Group(1L, "Group 1", new HashSet<>());

        // Create an updated group
        Group updatedGroup = new Group(1L, "Updated Group", new HashSet<>());

        // Mock the repository's findById method to return the existing group
        when(groupRepository.findById(1L)).thenReturn(Optional.of(existingGroup));

        // Mock the repository's save method to return the updated group
        when(groupRepository.save(existingGroup)).thenReturn(updatedGroup);

        // Call the service method
        Group result = groupService.updateGroup(1L, updatedGroup);

        // Verify the result
        assertEquals(updatedGroup, result);
        assertEquals(updatedGroup.getName(), result.getName());
    }

    @Test
    public void testDeleteGroup() {
        // Call the service method
        groupService.deleteGroup(1L);

        // Verify that the repository's deleteById method is called with the correct ID
        verify(groupRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testAddUserToGroup() {
        // Create a group
        Group group = new Group(1L, "Group 1", new HashSet<>());

        // Create a user to add to the group
        User user = User.builder().id(1).firstName("John Doe").build();



        // Mock the repository's findById method to return the group
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        // Call the service method
        Group result = groupService.addUserToGroup(1L, user);

        // Verify the result
        assertEquals(group, result);
        assertTrue(result.getMembers().contains(user));
        verify(groupRepository, times(1)).save(group);
    }

    // Add more test methods for other service methods as needed...
}