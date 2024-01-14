package com.group8.projectpfe.services.Impl;

import com.group8.projectpfe.entities.Group;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// GroupService.java
@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId).orElse(null);
    }

    public Group createGroup(Group group) {
        // Additional validation or business logic can be added here
        return groupRepository.save(group);
    }

    public Group updateGroup(Long groupId, Group updatedGroup) {
        Group existingGroup = groupRepository.findById(groupId).orElse(null);

        if (existingGroup != null) {
            // Update properties as needed
            existingGroup.setName(updatedGroup.getName());
            existingGroup.setMembers(updatedGroup.getMembers());

            return groupRepository.save(existingGroup);
        } else {
            return null; // Group not found
        }
    }

    public void deleteGroup(Long groupId) {
        groupRepository.deleteById(groupId);
    }
    public Group addUserToGroup(Long groupId, User user) {
        Group group = groupRepository.findById(groupId).orElse(null);

        if (group != null) {
            group.getMembers().add(user);
            groupRepository.save(group);
            return group;
        } else {
            return null; // Group not found
        }
    }
}
