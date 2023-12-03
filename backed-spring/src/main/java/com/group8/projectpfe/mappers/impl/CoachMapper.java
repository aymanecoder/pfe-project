package com.group8.projectpfe.mappers.impl;

import com.group8.projectpfe.domain.dto.CoachDTO;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CoachMapper implements Mapper<User, CoachDTO> {
    private final ModelMapper modelMapper;
    @Override
    public CoachDTO mapTo(User user) {
        return modelMapper.map(user,CoachDTO.class);
    }

    @Override
    public User mapFrom(CoachDTO coachDTO) {
        return modelMapper.map(coachDTO,User.class);
    }
}
