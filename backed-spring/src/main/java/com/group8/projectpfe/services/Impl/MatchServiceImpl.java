package com.group8.projectpfe.services.Impl;

import com.group8.projectpfe.domain.dto.MatchDto;
import com.group8.projectpfe.domain.dto.SportifDTO;
import com.group8.projectpfe.domain.dto.TeamDTO;
import com.group8.projectpfe.entities.Match;
import com.group8.projectpfe.entities.Sport;
import com.group8.projectpfe.entities.Team;
import com.group8.projectpfe.entities.User;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final MatchMapperImpl matchMapper;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final SportRepository sportRepository;
    private final TeamMapperImpl teamMapper;
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
        List<Match> matches = matchRepository.findAllWithTeams();
        return matches.stream()
                .map(matchMapper::mapTo)
                .collect(Collectors.toList());
    }


    @Override
    public MatchDto createMatch(MatchDto matchDto) {
        Match match;

        if (matchDto.getId() != null) {
            // If ID is present, load the entity from the database
            match = matchRepository.findById(matchDto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Match not found with ID: " + matchDto.getId()));

            // Update the loaded entity with data from the DTO
            matchMapper.updateMatchFromDto(matchDto, match);

            // Check and associate existing Teams
            List<Team> teams = matchDto.getTeams().stream()
                    .map(teamDto -> teamRepository.findById(teamDto.getId())
                            .orElseThrow(() -> new EntityNotFoundException("Team not found with ID: " + teamDto.getId())))
                    .collect(Collectors.toList());

            match.setTeams(teams);

            // Check and associate existing Sport
            Sport existingSport = sportRepository.findById(matchDto.getTypeDeSport().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Sport not found with ID: " + matchDto.getTypeDeSport().getId()));
            match.setTypeDeSport(existingSport);

            // Explicitly manage the state of participants
            List<User> managedParticipants = new ArrayList<>();
            for (SportifDTO participantDto : matchDto.getParticipants()) {
                User participant = userRepository.findById(participantDto.getId())
                        .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + participantDto.getId()));
                managedParticipants.add(participant);
            }

            match.setParticipants(managedParticipants);

        } else {
            // If ID is not present, create a new entity
            match = matchMapper.mapFrom(matchDto);

            // Manage the state of Teams
            List<Team> managedTeams = new ArrayList<>();
            for (Team team : match.getTeams()) {
                if (team.getId() != null) {
                    Team existingTeam = teamRepository.findById(team.getId())
                            .orElseThrow(() -> new EntityNotFoundException("Team not found with ID: " + team.getId()));
                    managedTeams.add(existingTeam);
                } else {
                    managedTeams.add(team);
                }
            }
            match.setTeams(managedTeams);

            // Manage the state of Sport
            if (match.getTypeDeSport() != null && match.getTypeDeSport().getId() != null) {
                Sport existingSport = sportRepository.findById(match.getTypeDeSport().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Sport not found with ID: " + match.getTypeDeSport().getId()));
                match.setTypeDeSport(existingSport);
            }
        }

        // Save the Match
        Match savedMatch = matchRepository.save(match);
        return matchMapper.mapTo(savedMatch);
    }

    @Override
    public MatchDto updateMatch(int matchId, MatchDto updatedMatchDto) {
        Match existingMatch = matchRepository.findById(matchId)
                .orElseThrow(() -> new EntityNotFoundException("Match not found with ID: " + matchId));

        // Update fields from DTO
        matchMapper.updateMatchFromDto(updatedMatchDto, existingMatch);

        // Manage the state of Teams
        List<Team> managedTeams = new ArrayList<>();
        for (TeamDTO teamDTO : updatedMatchDto.getTeams()) {
            if (teamDTO.getId() != null) {
                // If ID is present, load the entity from the database
                Team existingTeam = teamRepository.findById(teamDTO.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Team not found with ID: " + teamDTO.getId()));
                managedTeams.add(existingTeam);
            } else {
                // If ID is not present, it's a new team, so map the DTO to an entity
                Team newTeam = teamMapper.mapFrom(teamDTO);
                managedTeams.add(newTeam);
            }
        }
        existingMatch.setTeams(managedTeams);


        // Manage the state of Sport
        if (updatedMatchDto.getTypeDeSport() != null && updatedMatchDto.getTypeDeSport().getId() != null) {
            Sport existingSport = sportRepository.findById(updatedMatchDto.getTypeDeSport().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Sport not found with ID: " + updatedMatchDto.getTypeDeSport().getId()));
            existingMatch.setTypeDeSport(existingSport);
        }

        // Manage the state of Participants
        List<User> managedParticipants = new ArrayList<>();
        for (SportifDTO participantDto : updatedMatchDto.getParticipants()) {
            User participant = userRepository.findById(participantDto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + participantDto.getId()));
            managedParticipants.add(participant);
        }
        existingMatch.setParticipants(managedParticipants);

        // Save the updated Match
        matchRepository.save(existingMatch);
        return updatedMatchDto;
    }



    @Override
    public void deleteMatch(Integer matchId) {
        matchRepository.deleteById(matchId);
    }
}