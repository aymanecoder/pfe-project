package com.group8.projectpfe.services.Impl;

import com.group8.projectpfe.domain.dto.MatchDTO;
import com.group8.projectpfe.entities.Match;
import com.group8.projectpfe.mappers.impl.MatchMapper;
import com.group8.projectpfe.repositories.MatchRepository;
import com.group8.projectpfe.services.MatchService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final MatchMapper matchMapper;
    private final ModelMapper modelMapper;

    public MatchServiceImpl(MatchRepository matchRepository, MatchMapper matchMapper, ModelMapper modelMapper) {
        this.matchRepository = matchRepository;
        this.matchMapper = matchMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public MatchDTO createMatch(MatchDTO matchDTO) {
        Match match = matchMapper.mapFrom(matchDTO);
        Match savedMatch = matchRepository.save(match);
        return matchMapper.mapTo(savedMatch);
    }

    @Override
    public MatchDTO getMatchById(Integer id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Match not found with ID: " + id));
        return matchMapper.mapTo(match);
    }

    @Override
    public MatchDTO updateMatch(Integer id, MatchDTO matchDTO) {
        Match existingMatch = matchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Match not found with ID: " + id));

        // Map the updated data from DTO to the existing match entity
        modelMapper.map(matchDTO, existingMatch);

        // Save the updated match entity
        Match updatedMatch = matchRepository.save(existingMatch);
        return matchMapper.mapTo(updatedMatch);
    }

    @Override
    public List<MatchDTO> getAllMatchs() {
        List<Match> matches = matchRepository.findAll();
        return matches.stream()
                .map(matchMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMatch(Integer id) {
        if (!matchRepository.existsById(id)) {
            throw new EntityNotFoundException("Match not found with ID: " + id);
        }
        matchRepository.deleteById(id);
    }
}
