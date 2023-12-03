package com.group8.projectpfe.mappers.impl;

import com.group8.projectpfe.domain.dto.SportifDTO;
import com.group8.projectpfe.domain.dto.VideoDto;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.entities.VideoEntity;
import com.group8.projectpfe.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SportifMapper implements Mapper<User, SportifDTO> {
    private final ModelMapper modelMapper;
    @Override
    public SportifDTO mapTo(User user) {
        return modelMapper.map(user,SportifDTO.class);
    }

    @Override
    public User mapFrom(SportifDTO sportifDTO) {
        return modelMapper.map(sportifDTO,User.class);
    }
}
