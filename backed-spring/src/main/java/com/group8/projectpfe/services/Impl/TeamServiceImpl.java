package com.group8.projectpfe.services.Impl;

import com.group8.projectpfe.domain.dto.ProgrammeDTO;
import com.group8.projectpfe.domain.dto.SportDTO;
import com.group8.projectpfe.domain.dto.SportifDTO;
import com.group8.projectpfe.domain.dto.TeamDTO;
import com.group8.projectpfe.entities.Programme;
import com.group8.projectpfe.entities.Sport;
import com.group8.projectpfe.entities.Team;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.mappers.impl.SportifMapper;
import com.group8.projectpfe.mappers.impl.TeamMapperImpl;
import com.group8.projectpfe.repositories.SportRepository;
import com.group8.projectpfe.repositories.TeamRepository;
import com.group8.projectpfe.repositories.UserRepository;
import com.group8.projectpfe.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final SportifMapper sportifMapper;
    private final TeamRepository teamRepository;
    private final SportRepository sportRepository;
    private final UserRepository userRepository;
    private final TeamMapperImpl teamMapper;

    @Override
    public List<TeamDTO> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        return teams.stream().map(teamMapper::mapTo).collect(Collectors.toList());
    }

    @Override
    public Optional<TeamDTO> getTeamById(int id) {
        Optional<Team> teamOptional = teamRepository.findById(id);
        return teamOptional.map(teamMapper::mapTo);
    }

    @Override
    public TeamDTO createTeam(TeamDTO teamDetails) {
        // Assuming EntityManager is available or injected
        Team teamToCreate = teamMapper.mapFrom(teamDetails);

        // Retrieve managed admin user
        User managedAdmin = userRepository.getById(teamToCreate.getAdmin().getId());

        // Retrieve and manage members
        List<User> managedMembers = new ArrayList<>();
        for (User member : teamToCreate.getMembers()) {
            User managedMember = userRepository.getById(member.getId());
            managedMembers.add(managedMember);
        }

        // Check if the sport already exists
        Sport sport = sportRepository.findById(teamToCreate.getSport().getId()).orElse(null);

        // If the sport doesn't exist, create a new one
        if (sport == null) {
            sport = sportRepository.save(teamToCreate.getSport());
        }

        teamToCreate.setSport(sport);
        teamToCreate.setAdmin(managedAdmin);
        teamToCreate.setMembers(managedMembers);

        Team savedTeam = teamRepository.save(teamToCreate);
        return teamMapper.mapTo(savedTeam);
    }

    @Override
    public TeamDTO updateTeam(int id, TeamDTO updatedTeamDetails) {
        Optional<Team> optionalTeam = teamRepository.findById(id);

        if (optionalTeam.isPresent()) {
            Team existingTeam = optionalTeam.get();
            List<SportifDTO> sportifDTOList = updatedTeamDetails.getMembers();
            User user=sportifMapper.mapFrom(updatedTeamDetails.getAdmin());
            existingTeam.setAdmin(user);
            List<User> userList = sportifDTOList.stream()
                    .map(sportifMapper::mapFrom)
                    .collect(Collectors.toList());
            existingTeam.setMembers(userList);
            existingTeam.setLogo(updatedTeamDetails.getLogo());
            existingTeam.setDescription(updatedTeamDetails.getDescription());

            Team updatedTeam = teamRepository.save(existingTeam);
            return teamMapper.mapTo(updatedTeam);
        } else {
            // Handle scenario when the team with given ID is not found
            return null;
        }
    }

    @Override
    public void deleteTeam(int id) {
        teamRepository.deleteById(id);
    }

    @Override
    public List<TeamDTO> searchByDescription(String description) {
        List<Team> teamsByDescription = teamRepository.findByDescription(description);
        return teamsByDescription.stream().map(teamMapper::mapTo).collect(Collectors.toList());
    }


}
