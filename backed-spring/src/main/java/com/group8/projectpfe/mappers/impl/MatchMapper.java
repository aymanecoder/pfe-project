package com.group8.projectpfe.mappers.impl;

import com.group8.projectpfe.domain.dto.MatchDTO;
import com.group8.projectpfe.entities.Match;
import com.group8.projectpfe.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchMapper implements Mapper<Match, MatchDTO> {
    private final ModelMapper modelMapper;

    @Override
    public MatchDTO mapTo(Match match) {
        return modelMapper.map(match, MatchDTO.class);
    }

    @Override
    public Match mapFrom(MatchDTO matchDTO) {
        return modelMapper.map(matchDTO, Match.class);
    }
}
