package com.group8.projectpfe.services.Impl;


import com.group8.projectpfe.domain.dto.GroupDto;
import com.group8.projectpfe.entities.Group;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.mappers.impl.GroupMapper;
import com.group8.projectpfe.repositories.GroupRepository;
import com.group8.projectpfe.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final UserRepository userRepository;

    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
        this.userRepository = userRepository;
    }

        @Transactional
        public GroupDto createGroup(GroupDto groupDto) {
            Group groupToCreate = groupMapper.mapFrom(groupDto);

            List<User> managedMembers = new ArrayList<>();
            for (User member : groupToCreate.getMembers()) {
                User managedMember = userRepository.getById(member.getId());
                managedMembers.add(managedMember);
            }

            groupToCreate.setMembers(managedMembers);

            // Save the group
            Group createdGroup = groupRepository.save(groupToCreate);

            return groupMapper.mapTo(createdGroup);
        }

    @Transactional(readOnly = true)
    public List<GroupDto> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream()
                .map(groupMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GroupDto getGroupById(Long groupId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        return groupOptional.map(groupMapper::mapTo).orElse(null);
    }

    @Transactional
    public GroupDto updateGroup(Long groupId, GroupDto updatedGroupDto) {
        Optional<Group> existingGroupOptional = groupRepository.findById(groupId);

        if (existingGroupOptional.isPresent()) {
            Group existingGroup = existingGroupOptional.get();
            groupMapper.mapToEntity(updatedGroupDto, existingGroup);
            Group updatedGroup = groupRepository.save(existingGroup);
            return groupMapper.mapTo(updatedGroup);
        } else {
            // Handle the case where the group with the given ID is not found
            return null;
        }
    }

    @Transactional
    public void deleteGroup(Long groupId) {
        groupRepository.deleteById(groupId);
    }
}
