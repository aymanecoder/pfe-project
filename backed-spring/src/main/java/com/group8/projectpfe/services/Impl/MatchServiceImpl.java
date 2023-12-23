package com.group8.projectpfe.services.Impl;

import com.group8.projectpfe.domain.dto.MatchDto;
import com.group8.projectpfe.entities.Match;
import com.group8.projectpfe.mappers.impl.MatchMapperImpl;
import com.group8.projectpfe.repositories.MatchRepository;
import com.group8.projectpfe.services.MatchService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final MatchMapperImpl matchMapper;

    public MatchServiceImpl(MatchRepository matchRepository, MatchMapperImpl matchMapper) {
        this.matchRepository = matchRepository;
        this.matchMapper = matchMapper;
    }

    @Override
    public MatchDto getMatchById(Integer matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new EntityNotFoundException("Match not found with id: " + matchId));

        return matchMapper.mapTo(match);
    }

    @Override
    public List<MatchDto> getAllMatches() {
        List<Match> matches = matchRepository.findAll();
        return matches.stream()
                .map(matchMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public MatchDto createMatch(MatchDto MatchDto) {
        Match match = matchMapper.mapFrom(MatchDto);
        Match savedMatch = matchRepository.save(match);
        return matchMapper.mapTo(savedMatch);
    }

    @Override
    public MatchDto updateMatch(Integer matchId, MatchDto MatchDto) {
        Match existingMatch = matchRepository.findById(matchId)
                .orElseThrow(() -> new EntityNotFoundException("Match not found with id: " + matchId));

        // Update fields with non-null values from the DTO
        matchMapper.updateMatchFromDto(MatchDto, existingMatch);

        return matchMapper.mapTo(existingMatch);
    }

    @Override
    public void deleteMatch(Integer matchId) {
        matchRepository.deleteById(matchId);
    }
}