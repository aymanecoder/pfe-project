package com.group8.projectpfe.services.Impl;

import com.group8.projectpfe.domain.dto.MatchDto;
import com.group8.projectpfe.domain.dto.SportifDTO;
import com.group8.projectpfe.domain.dto.TeamDTO;
import com.group8.projectpfe.entities.*;
import com.group8.projectpfe.mappers.impl.MatchMapperImpl;
import com.group8.projectpfe.mappers.impl.TeamMapperImpl;
import com.group8.projectpfe.repositories.MatchRepository;
import com.group8.projectpfe.repositories.SportRepository;
import com.group8.projectpfe.repositories.TeamRepository;
import com.group8.projectpfe.repositories.UserRepository;
import com.group8.projectpfe.services.MatchService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final MatchMapperImpl matchMapper;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final SportRepository sportRepository;
    @PersistenceContext
    private EntityManager entityManager;

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
                .map(match -> {
                    MatchDto matchDto = matchMapper.mapTo(match);
                    if (match.getTypeMatch() == MatchType.UPCOMING) {
                        matchDto.setDate(match.getDate());
                        matchDto.setCounter(calculateMatchCounter(match));
                        matchDto.setScoreTeamA(0);
                        matchDto.setScoreTeamB(0);
                    } else {
                        matchDto.setScoreTeamA(match.getScoreTeamA());
                        matchDto.setScoreTeamB(match.getScoreTeamB());
                    }

                    return matchDto;
                })
                .collect(Collectors.toList());
    }
    private int calculateMatchCounter(Match match) {

        return (int) ChronoUnit.DAYS.between(LocalDateTime.now(), match.getDate());
    }
    @Override
    @Transactional
    public MatchDto createMatch(MatchDto matchDto) {

        Match matchToCreate = matchMapper.mapFrom(matchDto);

        List<Team> teams = new ArrayList<>();
        for (Team team : matchToCreate.getTeams()) {
            Team managedTeam = teamRepository.getById(team.getId());
            teams.add(managedTeam);
        }

        matchToCreate.setTeams(teams);
        // Update participants
        List<User> managedMembers = new ArrayList<>();
        for (SportifDTO member : matchDto.getParticipants()) {
            User managedMember = userRepository.getById(member.getId());
            managedMembers.add(managedMember);
        }

        matchToCreate.setParticipants(managedMembers);

        // Retrieve the existing sport from the database
        Sport existingSport = sportRepository.findById(matchToCreate.getSport().getId()).orElse(null);

        if (existingSport != null) {
            matchToCreate.setSport(existingSport);
        }
        Match savedMatch = matchRepository.save(matchToCreate);
        return matchMapper.mapTo(savedMatch);
    }


    @Override
    public MatchDto updateMatch(MatchDto updatedMatchDetails) {
        Optional<Match> optionalMatch = matchRepository.findById(updatedMatchDetails.getId());

        if (optionalMatch.isPresent()) {
            Match existingMatch = optionalMatch.get();



            Sport sport = sportRepository.getReferenceById(updatedMatchDetails.getSport().getId());
            existingMatch.setSport(sport);
            // Assuming you're getting the members as a list of User IDs in the DTO
            List<Integer> memberIds = updatedMatchDetails.getParticipants().stream()
                    .map(SportifDTO::getId) // Assuming getId() returns the user ID as an Integer
                    .collect(Collectors.toList());
            List<User> members = userRepository.findAllById(memberIds);
            existingMatch.setParticipants(members);

            List<Integer> teamIds = updatedMatchDetails.getTeams().stream()
                    .map(TeamDTO::getId) // Assuming getId() returns the user ID as an Integer
                    .collect(Collectors.toList());
            List<Team> teams = teamRepository.findAllById(teamIds);
            existingMatch.setTeams(teams);
//
//            existingMatch.setLogoPath(updatedMatchDetails.getLogoPath());
            existingMatch.setDescription(updatedMatchDetails.getDescription());

            Match updatedMatch = matchRepository.save(existingMatch);
            return matchMapper.mapTo(updatedMatch);
        } else {
            // Handle scenario when the team with the given ID is not found
            return null;
        }
    }


    @Override
    public void deleteMatch(Integer matchId) {
        matchRepository.deleteById(matchId);
    }
}