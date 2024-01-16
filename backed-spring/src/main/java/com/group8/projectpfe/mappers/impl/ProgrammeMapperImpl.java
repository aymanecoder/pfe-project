package com.group8.projectpfe.mappers.impl;

import com.group8.projectpfe.domain.dto.ProgrammeDTO;
import com.group8.projectpfe.entities.Programme;
import com.group8.projectpfe.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProgrammeMapperImpl implements Mapper<Programme, ProgrammeDTO> {

    private final ModelMapper modelMapper;
    @Override
    public ProgrammeDTO mapTo(Programme Programme) {
        return modelMapper.map(Programme,ProgrammeDTO.class);
    }

    @Override
    public Programme mapFrom(ProgrammeDTO Programmedto) {
        return modelMapper.map(Programmedto,Programme.class);
    }
}
