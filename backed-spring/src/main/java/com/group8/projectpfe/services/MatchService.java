package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.MatchDto;

import java.util.List;

public interface MatchService {

    MatchDto getMatchById(Integer matchId);

    List<MatchDto> getAllMatches();

    MatchDto createMatch(MatchDto MatchDto);

    MatchDto updateMatch(Integer matchId, MatchDto MatchDto);

    void deleteMatch(Integer matchId);
}
