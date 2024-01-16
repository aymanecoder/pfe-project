package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.MatchDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface MatchService {

    MatchDto getMatchById(Integer matchId);

    List<MatchDto> getAllMatches();

    MatchDto createMatch(MatchDto MatchDto);

    @Transactional
    public MatchDto updateMatch(MatchDto matchDto) ;

    void deleteMatch(Integer matchId);


}
