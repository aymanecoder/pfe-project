package com.group8.projectpfe.mappers.impl;

import com.group8.projectpfe.domain.dto.GroupDto;
import com.group8.projectpfe.entities.Group;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GroupMapper implements Mapper<Group, GroupDto> {

    private final ModelMapper modelMapper;



    @Override
    public GroupDto mapTo(Group group) {
        return modelMapper.map(group, GroupDto
                .class);
    }

    @Override
    public Group mapFrom(GroupDto groupDTO) {
        Group group = modelMapper.map(groupDTO, Group.class);

        // Map the members
        List<User> members = groupDTO.getMembers().stream()
                .map(memberDTO -> modelMapper.map(memberDTO, User.class))
                .collect(Collectors.toList());
        group.setMembers(members);

        return group;
    }
    public void mapToEntity(GroupDto groupDto, Group group) {
        // Map the properties from the DTO to the existing entity
        modelMapper.map(groupDto, group);

        // Map the members
        List<User> members = groupDto.getMembers().stream()
                .map(memberDTO -> modelMapper.map(memberDTO, User.class))
                .collect(Collectors.toList());
        group.setMembers(members);
    }
}
