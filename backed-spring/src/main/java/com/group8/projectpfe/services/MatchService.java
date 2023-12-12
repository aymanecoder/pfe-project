package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.MatchDTO;

import java.util.List;

public interface MatchService {
    MatchDTO createMatch(MatchDTO Match);
    MatchDTO getMatchById(Integer id);
    MatchDTO updateMatch(Integer id,MatchDTO Match);
    List<MatchDTO> getAllMatchs();
    void deleteMatch(Integer id);
}
