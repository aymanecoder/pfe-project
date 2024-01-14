package com.group8.projectpfe.controllers;

import com.group8.projectpfe.domain.dto.GroupDto;
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
        List<GroupDto> groupDtos = Arrays.asList(new GroupDto(), new GroupDto());
        Mockito.when(groupService.getAllGroups()).thenReturn(groupDtos);

        // Perform the GET request
        List<GroupDto> result = (List<GroupDto>) groupController.getAllGroups();

        // Verify the result
        Assert.assertEquals(groupDtos, result);
    }

    @Test
    public void testGetGroupById() {
        // Mock data
        Long groupId = 1L;
        GroupDto groupDto = new GroupDto();
        Mockito.when(groupService.getGroupById(groupId)).thenReturn(groupDto);

        // Perform the GET request
        ResponseEntity<GroupDto> response = groupController.getGroupById(groupId);

        // Verify the result
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(groupDto, response.getBody());
    }

    @Test
    public void testGetGroupByIdNotFound() {
        // Mock data
        Long groupId = 1L;
        Mockito.when(groupService.getGroupById(groupId)).thenReturn(null);

        // Perform the GET request
        ResponseEntity<GroupDto> response = groupController.getGroupById(groupId);

        // Verify the result
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateGroup() {
        // Mock data
        GroupDto groupDtoToCreate = new GroupDto();
        GroupDto createdGroupDto = new GroupDto();
        Mockito.when(groupService.createGroup(groupDtoToCreate)).thenReturn(createdGroupDto);

        // Perform the POST request
        ResponseEntity<GroupDto> response = groupController.createGroup(groupDtoToCreate);

        // Verify the result
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assert.assertEquals(createdGroupDto, response.getBody());
    }
}
