package com.group8.projectpfe.services.Impl;

import com.group8.projectpfe.domain.dto.MatchDto;
import com.group8.projectpfe.entities.*;
import com.group8.projectpfe.mappers.impl.MatchMapperImpl;
import com.group8.projectpfe.repositories.MatchRepository;
import com.group8.projectpfe.repositories.SportRepository;
import com.group8.projectpfe.repositories.TeamRepository;
import com.group8.projectpfe.repositories.UserRepository;
import com.group8.projectpfe.services.MatchService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

        // Retrieve the existing sport from the database if the sportDto is not null
        if (matchDto.getSport() != null) {
            Sport existingSport = sportRepository.findById(matchDto.getSport().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Sport not found with id: " + matchDto.getSport().getId()));
            matchToCreate.setSport(existingSport);
        }

        List<Team> teams = matchDto.getTeams().stream()
                .map(teamDTO -> teamRepository.getById(teamDTO.getId()))
                .collect(Collectors.toList());
        matchToCreate.setTeams(teams);

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

            existingMatch.setDescription(updatedMatchDetails.getDescription());
            existingMatch.setScoreTeamA(updatedMatchDetails.getScoreTeamA());
            existingMatch.setScoreTeamB(updatedMatchDetails.getScoreTeamB());

            Match updatedMatch = matchRepository.save(existingMatch);
            return matchMapper.mapTo(updatedMatch);
        } else {
            // Handle scenario when the match with the given ID is not found
            return null;
        }
    }

    @Override
    public void deleteMatch(Integer matchId) {
        matchRepository.deleteById(matchId);
    }
}