package com.group8.projectpfe.mappers.impl;

import com.group8.projectpfe.domain.dto.ChallengeDTO;
import com.group8.projectpfe.entities.Challenge;
import com.group8.projectpfe.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChallengeMapperImpl implements Mapper<Challenge, ChallengeDTO> {

    private final ModelMapper modelMapper;

    @Override
    public ChallengeDTO mapTo(Challenge challenge) {
        return modelMapper.map(challenge, ChallengeDTO.class);
    }

    @Override
    public Challenge mapFrom(ChallengeDTO challengeDTO) {
        return modelMapper.map(challengeDTO, Challenge.class);
    }
}
