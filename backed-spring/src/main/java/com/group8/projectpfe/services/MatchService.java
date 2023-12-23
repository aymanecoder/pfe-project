package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.MatchDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface MatchService {

    MatchDto getMatchById(Integer matchId);

    List<MatchDto> getAllMatches();

    MatchDto createMatch(MatchDto MatchDto);

    @Transactional
    MatchDto updateMatch(int matchId, MatchDto updatedMatchDto);

    void deleteMatch(Integer matchId);
}
