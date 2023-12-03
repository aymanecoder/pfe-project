package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.CoachDTO;

import java.util.List;

public interface CoachService {
    List<CoachDTO> getCoachs();

    CoachDTO getCoachById(Long coachId);
    void deleteCoach(Integer id,Integer userId);

    void updateCoach(CoachDTO coachDTO);
}
