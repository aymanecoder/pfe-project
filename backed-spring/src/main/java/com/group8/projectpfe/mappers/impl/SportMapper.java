package com.group8.projectpfe.mappers.impl;

import com.group8.projectpfe.domain.dto.SportDTO;
import com.group8.projectpfe.entities.Sport;
import com.group8.projectpfe.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SportMapper implements Mapper<Sport, SportDTO> {
    private final ModelMapper modelMapper;

    @Override
    public SportDTO mapTo(Sport sport) {
        return modelMapper.map(sport, SportDTO.class);
    }

    @Override
    public Sport mapFrom(SportDTO sportDTO) {
        return modelMapper.map(sportDTO, Sport.class);
    }
}
