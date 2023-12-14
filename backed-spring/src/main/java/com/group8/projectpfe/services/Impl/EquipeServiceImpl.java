package com.group8.projectpfe.services.Impl;

import com.group8.projectpfe.domain.dto.SportifDTO;
import com.group8.projectpfe.domain.dto.EquipeDTO;
import com.group8.projectpfe.entities.Equipe;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.mappers.impl.SportifMapper;
import com.group8.projectpfe.mappers.impl.TeamMapperImpl;
import com.group8.projectpfe.repositories.EquipeRepository;
import com.group8.projectpfe.services.EquipeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class EquipeServiceImpl implements EquipeService {

    private final SportifMapper sportifMapper;
    private final EquipeRepository teamRepository;

    private final TeamMapperImpl teamMapper;

    @Override
    public List<EquipeDTO> getAllTeams() {
        List<Equipe> equipes = teamRepository.findAll();
        return equipes.stream().map(teamMapper::mapTo).collect(Collectors.toList());
    }

    @Override
    public Optional<EquipeDTO> getTeamById(int id) {
        Optional<Equipe> teamOptional = teamRepository.findById(id);
        return teamOptional.map(teamMapper::mapTo);
    }

    @Override
    @Transactional
    public EquipeDTO createTeam(EquipeDTO teamDetails) {

        Equipe equipeToCreate = teamMapper.mapFrom(teamDetails);
        Equipe savedEquipe = teamRepository.save(equipeToCreate);
        return teamMapper.mapTo(savedEquipe);
    }

    @Override
    public EquipeDTO updateTeam(int id, EquipeDTO updatedTeamDetails) {
        Optional<Equipe> optionalTeam = teamRepository.findById(id);

        if (optionalTeam.isPresent()) {
            Equipe existingEquipe = optionalTeam.get();
            List<SportifDTO> sportifDTOList = updatedTeamDetails.getMembers();
            User user=sportifMapper.mapFrom(updatedTeamDetails.getAdmin());
            existingEquipe.setAdmin(user);
            List<User> userList = sportifDTOList.stream()
                    .map(sportifMapper::mapFrom)
                    .collect(Collectors.toList());
            existingEquipe.setMembers(userList);
            existingEquipe.setLogo(updatedTeamDetails.getLogo());
            existingEquipe.setDescription(updatedTeamDetails.getDescription());

            Equipe updatedEquipe = teamRepository.save(existingEquipe);
            return teamMapper.mapTo(updatedEquipe);
        } else {

            return null;
        }
    }

    @Override
    public void deleteTeam(int id) {
        teamRepository.deleteById(id);
    }

    @Override
    public List<EquipeDTO> searchByDescription(String description) {
        List<Equipe> teamsByDescription = teamRepository.findByDescription(description);
        return teamsByDescription.stream().map(teamMapper::mapTo).collect(Collectors.toList());
    }


}
