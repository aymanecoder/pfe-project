package com.group8.projectpfe.services.Impl;

import com.group8.projectpfe.domain.dto.ChallengeDTO;
import com.group8.projectpfe.entities.Challenge;
import com.group8.projectpfe.mappers.impl.ChallengeMapperImpl;
import com.group8.projectpfe.repositories.ChallengeRepository;
import com.group8.projectpfe.services.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {


    private final ChallengeRepository challengeRepository;


    private final ChallengeMapperImpl challengeMapper;

    @Override
    public List<ChallengeDTO> getAllChallenges() {
        List<Challenge> challenges = challengeRepository.findAll();
        return challenges.stream().map(challengeMapper::mapTo).collect(Collectors.toList());
    }

    @Override
    public Optional<ChallengeDTO> getChallengeById(int id) {
        Optional<Challenge> challengeOptional = challengeRepository.findById(id);
        return challengeOptional.map(challengeMapper::mapTo);
    }

    @Override
    public ChallengeDTO createChallenge(ChallengeDTO challengeDetails) {
        Challenge challengeToCreate = challengeMapper.mapFrom(challengeDetails);
        Challenge savedChallenge = challengeRepository.save(challengeToCreate);
        return challengeMapper.mapTo(savedChallenge);
    }

    @Override
    public ChallengeDTO updateChallenge(int id, ChallengeDTO updatedChallengeDetails) {
        Optional<Challenge> optionalChallenge = challengeRepository.findById(id);

        if (optionalChallenge.isPresent()) {
            Challenge existingChallenge = optionalChallenge.get();
            Challenge updatedChallenge = challengeRepository.save(existingChallenge);
            return challengeMapper.mapTo(updatedChallenge);
        } else {
            return null;
        }
    }

    @Override
    public void deleteChallenge(int id) {
        challengeRepository.deleteById(id);
    }




}
