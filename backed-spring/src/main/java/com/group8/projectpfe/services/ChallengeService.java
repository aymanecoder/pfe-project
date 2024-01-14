package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.ChallengeDTO;
import com.group8.projectpfe.entities.User;

import java.util.List;
import java.util.Optional;

public interface ChallengeService {
    List<ChallengeDTO> getAllChallenges();

    Optional<ChallengeDTO> getChallengeById(int id);

    ChallengeDTO createChallenge(ChallengeDTO challengeDetails);

    ChallengeDTO updateChallenge(int id, ChallengeDTO updatedChallengeDetails);

    String joinChallenge(int challengeId, User user);
    void deleteChallenge(int id);


}
