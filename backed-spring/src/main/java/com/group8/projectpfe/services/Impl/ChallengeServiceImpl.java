package com.group8.projectpfe.services.Impl;

import com.group8.projectpfe.domain.dto.ChallengeDTO;
import com.group8.projectpfe.domain.dto.SportifDTO;
import com.group8.projectpfe.domain.dto.TeamDTO;
import com.group8.projectpfe.entities.*;
import com.group8.projectpfe.mappers.impl.ChallengeMapperImpl;
import com.group8.projectpfe.mappers.impl.SportMapperImpl;
import com.group8.projectpfe.mappers.impl.SportifMapper;
import com.group8.projectpfe.mappers.impl.TeamMapperImpl;
import com.group8.projectpfe.repositories.ChallengeRepository;
import com.group8.projectpfe.repositories.MatchRepository;
import com.group8.projectpfe.repositories.SportRepository;
import com.group8.projectpfe.repositories.TeamRepository;
import com.group8.projectpfe.services.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.group8.projectpfe.entities.MatchType.UPCOMING;

@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {


    private final ChallengeRepository challengeRepository;

    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;
    private final SportRepository sportRepository;
    private final SportMapperImpl sportMapperImpl;
    private final TeamMapperImpl teamMapperImpl;
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

        Sport existingSport = sportRepository.findById(challengeDetails.getSport().getId()).orElse(null);

        if (existingSport != null) {
            challengeToCreate.setSport(existingSport);
        }
        List<Team> teams = new ArrayList<>();
        if (challengeToCreate.getTeams() != null) {
            for (Team member : challengeToCreate.getTeams()) {
                Team team = teamRepository.getById(member.getId());
                teams.add(team);
            }
        }

        challengeToCreate.setTeams(teams);
        challengeToCreate.setLogoPath(challengeToCreate.getLogoPath());
        Challenge savedChallenge = challengeRepository.save(challengeToCreate);
        return challengeMapper.mapTo(savedChallenge);
    }

    @Override
    public ChallengeDTO updateChallenge(int id, ChallengeDTO updatedChallengeDetails) {
        Optional<Challenge> optionalChallenge = challengeRepository.findById(id);

        if (optionalChallenge.isPresent()) {
            Challenge existingChallenge = optionalChallenge.get();
        //updatedChallengeDetails.setSport(sportMapperImpl.mapTo(existingChallenge.getSport()));
//           if(updatedChallengeDetails.getTeams()==null) {
//               updatedChallengeDetails.setTeams(existingChallenge.getTeams().stream().map(teamMapperImpl::mapTo).collect(Collectors.toList()));
//           }
            if(updatedChallengeDetails.getSport()!=null) {
                Sport sport = sportRepository.getReferenceById(updatedChallengeDetails.getSport().getId());
                existingChallenge.setSport(sport);
            }
            // Assuming you're getting the members as a list of User IDs in the DTO
            if(updatedChallengeDetails.getTeams()!=null) {
                List<Integer> teamTds = updatedChallengeDetails.getTeams().stream()
                        .map(TeamDTO::getId) // Assuming getId() returns the user ID as an Integer
                        .collect(Collectors.toList());
                List<Team> teams = teamRepository.findAllById(teamTds);
                existingChallenge.setTeams(teams);
            }

            existingChallenge.setLogoPath(updatedChallengeDetails.getLogoPath());
            existingChallenge.setTitle(updatedChallengeDetails.getTitle());
            existingChallenge.setNbrTeams(updatedChallengeDetails.getNbrTeams());
            existingChallenge.setDescription(updatedChallengeDetails.getDescription());
            existingChallenge.setCreationDate(updatedChallengeDetails.getCreationDate());
            Challenge updatedChallenge = challengeRepository.save(existingChallenge);
            return challengeMapper.mapTo(updatedChallenge);
        } else {
            return null;
        }
    }

    @Override
    public String joinChallenge(int challengeId, User user) {
        // Check if the challenge exists
        Challenge challenge = challengeRepository.findById(challengeId).orElse(null);
        if (challenge == null) {
            return "Challenge not found";
        }

        // Check if the challenge is already full
        int numberOfTeams = challenge.getNbrTeams();
        List<Team> teams = challenge.getTeams();

        if (numberOfTeams == teams.size()) {
            return "Challenge is already full";
        }

        // Check if the user is already in a team for this challenge
        Team userTeam = user.getTeam();
        if (userTeam == null) {
            return "User doesn't have team";
        }

        if (challenge.getTeams() == null) {
            challenge.setTeams(new ArrayList<>());
        }


        if (userTeam != null  && challenge.getTeams().contains(userTeam)) {
            return "the team is already in the challenge";
        }

        // Check if the user is an admin in their team
        if (userTeam != null && userTeam.getAdmin() != null && !userTeam.getAdmin().equals(user)) {
            return "User is not an admin in their team";
        }

        // If all checks pass, add the user to the challenge
        challenge.getTeams().add(userTeam);

        challengeRepository.save(challenge);

        if (challenge.getTeams().size() == challenge.getNbrTeams()) {
            createMatchesForChallenge(challenge);
        }
        // Additional logic, such as creating matches, can be added here

        return "User joined the challenge successfully";
    }

    @Override
    public void deleteChallenge(int id) {
        challengeRepository.deleteById(id);
    }


    private void createMatchesForChallenge(Challenge challenge) {
        List<Team> teams = challenge.getTeams();

        // Shuffle the list of teams to randomize the match pairings
        Collections.shuffle(teams);

        // Create matches by pairing up teams
        int matchesToCreate = teams.size() / 2;

        // Create matches by pairing up teams
        List<Match> matches = new ArrayList<>();
        for (int i = 0; i < matchesToCreate; i++) {
            Team team1 = teams.get(i * 2);
            Team team2 = teams.get(i * 2 + 1);

            // Create a match with the paired teams
            Match match = new Match();
            match.setTeams(Arrays.asList(team1, team2));
            match.setSport(challenge.getSport());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            match.setDate(LocalDateTime.parse(challenge.getCreationDate(), formatter));

            match.setTypeMatch(UPCOMING);
            match.setPrivate(false);
            match.setDescription(challenge.getDescription());
            match.setTitle(challenge.getTitle());
            // Add the match to the list
            matches.add(match);
        }

        matchRepository.saveAll(matches);
    }


}
