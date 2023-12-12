package com.group8.projectpfe.mappers.impl;

import com.group8.projectpfe.domain.dto.EquipeDTO;
import com.group8.projectpfe.entities.Equipe;
import com.group8.projectpfe.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EquipeMapper implements Mapper<Equipe, EquipeDTO> {
    private final ModelMapper modelMapper;

    @Override
    public EquipeDTO mapTo(Equipe equipe) {
        return modelMapper.map(equipe, EquipeDTO.class);
    }

    @Override
    public Equipe mapFrom(EquipeDTO equipeDTO) {
        return modelMapper.map(equipeDTO, Equipe.class);
    }
}
