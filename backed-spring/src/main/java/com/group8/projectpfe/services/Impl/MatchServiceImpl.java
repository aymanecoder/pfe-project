package com.group8.projectpfe.services.Impl;

import com.group8.projectpfe.domain.dto.MatchDto;
import com.group8.projectpfe.entities.Match;
import com.group8.projectpfe.entities.Sport;
import com.group8.projectpfe.entities.Team;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.mappers.impl.MatchMapperImpl;
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
                .map(matchMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MatchDto createMatch(MatchDto matchDto) {
        Match match;

        if (matchDto.getId() != null) {
            // Existing match, load from the database
            match = matchRepository.findById(matchDto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Match not found with ID: " + matchDto.getId()));

            // Update fields from the DTO
            matchMapper.updateMatchFromDto(matchDto, match);

            // Update teams
            List<Team> teams = matchDto.getTeams().stream()
                    .map(teamDto -> teamRepository.findById(teamDto.getId())
                            .orElseThrow(() -> new EntityNotFoundException("Team not found with ID: " + teamDto.getId())))
                    .collect(Collectors.toList());

            match.setTeams(teams);

            // Update participants
            List<User> participants = matchDto.getParticipants().stream()
                    .map(userDto -> userRepository.findById(userDto.getId())
                            .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userDto.getId())))
                    .collect(Collectors.toList());

            match.setParticipants(participants);

            // Update sport
            Sport existingSport = sportRepository.findById(matchDto.getTypeDeSport().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Sport not found with ID: " + matchDto.getTypeDeSport().getId()));
            match.setTypeDeSport(existingSport);

        } else {
            // New match, create a new entity
            match = matchMapper.mapFrom(matchDto);
        }

        // Save the Match
        Match savedMatch = matchRepository.save(match);
        return matchMapper.mapTo(savedMatch);
    }


    @Override
    public MatchDto updateMatch(MatchDto matchDto) {
        Match existingMatch = matchRepository.findById(matchDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Match not found with ID: " + matchDto.getId()));

        // Update existingMatch with data from matchDto
        matchMapper.updateMatchFromDto(matchDto, existingMatch);

        // Reattach detached User entities to the persistence context
        userRepository.saveAll(existingMatch.getParticipants());

        // Save the updated Match
        Match savedMatch = matchRepository.save(existingMatch);

        return matchMapper.mapTo(savedMatch);
    }


    @Override
    public void deleteMatch(Integer matchId) {
        matchRepository.deleteById(matchId);
    }
}