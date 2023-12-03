package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.ChallengeDTO;
import java.util.List;
import java.util.Optional;

public interface ChallengeService {
    List<ChallengeDTO> getAllChallenges();

    Optional<ChallengeDTO> getChallengeById(int id);

    ChallengeDTO createChallenge(ChallengeDTO challengeDetails);

    ChallengeDTO updateChallenge(int id, ChallengeDTO updatedChallengeDetails);

    void deleteChallenge(int id);

    List<ChallengeDTO> searchByTitle(String title);

    // Other service methods can be declared here
}
