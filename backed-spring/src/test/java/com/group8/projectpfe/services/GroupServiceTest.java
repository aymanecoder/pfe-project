package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.GroupDto;
import com.group8.projectpfe.entities.Group;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.mappers.impl.GroupMapper;
import com.group8.projectpfe.repositories.GroupRepository;
import com.group8.projectpfe.repositories.UserRepository;
import com.group8.projectpfe.services.Impl.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GroupServiceTest {

    private GroupService groupService;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private GroupMapper groupMapper;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        groupService = new GroupService(groupRepository, groupMapper, userRepository);
    }

    @Test
    public void testCreateGroup() {
        // Arrange
        GroupDto groupDto = new GroupDto();
        Group groupToCreate = new Group();
        groupToCreate.setMembers(new ArrayList<>());
        when(groupMapper.mapFrom(groupDto)).thenReturn(groupToCreate);
        when(userRepository.getById(any())).thenReturn(new User());
        when(groupRepository.save(groupToCreate)).thenReturn(groupToCreate);
        when(groupMapper.mapTo(groupToCreate)).thenReturn(groupDto);

        // Act
        GroupDto createdGroup = groupService.createGroup(groupDto);

        // Assert
        assertEquals(groupDto, createdGroup);
        verify(groupRepository, times(1)).save(groupToCreate);
    }

    @Test
    public void testGetAllGroups() {
        // Arrange
        List<Group> groups = new ArrayList<>();
        groups.add(new Group());
        when(groupRepository.findAll()).thenReturn(groups);
        when(groupMapper.mapTo(any(Group.class))).thenReturn(new GroupDto());

        // Act
        List<GroupDto> allGroups = groupService.getAllGroups();

        // Assert
        assertEquals(groups.size(), allGroups.size());
    }

    @Test
    public void testGetGroupById() {
        // Arrange
        Long groupId = 1L;
        Group group = new Group();
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
        when(groupMapper.mapTo(group)).thenReturn(new GroupDto());

        // Act
        GroupDto retrievedGroup = groupService.getGroupById(groupId);

        // Assert
//        assertEquals(group, retrievedGroup);
    }

    @Test
    public void testUpdateGroup() {
        // Arrange
        Long groupId = 1L;
        GroupDto updatedGroupDto = new GroupDto();
        Group existingGroup = new Group();
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(existingGroup));
        when(groupRepository.save(existingGroup)).thenReturn(existingGroup);
        when(groupMapper.mapTo(existingGroup)).thenReturn(updatedGroupDto);

        // Act
        GroupDto updatedGroup = groupService.updateGroup(groupId, updatedGroupDto);

        // Assert
        assertEquals(updatedGroupDto, updatedGroup);
    }

    @Test
    public void testDeleteGroup() {
        // Arrange
        Long groupId = 1L;

        // Act
        groupService.deleteGroup(groupId);

        // Assert
        verify(groupRepository, times(1)).deleteById(groupId);
    }
}