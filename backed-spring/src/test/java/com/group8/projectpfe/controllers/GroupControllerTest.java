package com.group8.projectpfe.controllers;

import com.group8.projectpfe.entities.Group;
import com.group8.projectpfe.services.Impl.GroupService;
import com.group8.projectpfe.services.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class GroupControllerTest {

    @Mock
    private GroupService groupService;

    @Mock
    private UserService userService;

    @InjectMocks
    private GroupController groupController;

    @Test
    public void testGetAllGroups() {
        // Mock data
        List<Group> groups = Arrays.asList(new Group(), new Group());
        Mockito.when(groupService.getAllGroups()).thenReturn(groups);

        // Perform the GET request
        List<Group> result = groupController.getAllGroups();

        // Verify the result
        Assert.assertEquals(groups, result);
    }

    @Test
    public void testGetGroupById() {
        // Mock data
        Long groupId = 1L;
        Group group = new Group();
        Mockito.when(groupService.getGroupById(groupId)).thenReturn(group);

        // Perform the GET request
        ResponseEntity<Group> response = groupController.getGroupById(groupId);

        // Verify the result
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(group, response.getBody());
    }

    @Test
    public void testGetGroupByIdNotFound() {
        // Mock data
        Long groupId = 1L;
        Mockito.when(groupService.getGroupById(groupId)).thenReturn(null);

        // Perform the GET request
        ResponseEntity<Group> response = groupController.getGroupById(groupId);

        // Verify the result
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Similarly, you can write test cases for other methods such as createGroup, updateGroup, deleteGroup, and joinGroup.
}