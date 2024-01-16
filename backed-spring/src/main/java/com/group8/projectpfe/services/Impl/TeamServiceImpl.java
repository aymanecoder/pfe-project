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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final ModelMapper modelMapper;



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

        // Retrieve the ID of the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer authenticatedUserId = ((User) authentication.getPrincipal()).getId();

        // Retrieve managed admin user
        User managedAdmin = userRepository.getById(authenticatedUserId);

        // Retrieve and manage members
        List<User> managedMembers = new ArrayList<>();
        for (User member : teamToCreate.getMembers()) {
            User managedMember = userRepository.getById(member.getId());
            managedMembers.add(managedMember);
        }

        // Retrieve the existing sport from the database
        Sport existingSport = sportRepository.findById(teamToCreate.getSport().getId()).orElse(null);

        if (existingSport != null) {
            teamToCreate.setSport(existingSport);
        }

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

            User user = userRepository.getById(updatedTeamDetails.getAdmin().getId());
            existingTeam.setAdmin(user);

            Sport sport = sportRepository.getReferenceById(updatedTeamDetails.getSport().getId());
            existingTeam.setSport(sport);
            // Assuming you're getting the members as a list of User IDs in the DTO
            List<Integer> memberIds = updatedTeamDetails.getMembers().stream()
                    .map(SportifDTO::getId) // Assuming getId() returns the user ID as an Integer
                    .collect(Collectors.toList());
            List<User> members = userRepository.findAllById(memberIds);
            existingTeam.setMembers(members);

            existingTeam.setLogoPath(updatedTeamDetails.getLogoPath());
            existingTeam.setDescription(updatedTeamDetails.getDescription());

            Team updatedTeam = teamRepository.save(existingTeam);
            return teamMapper.mapTo(updatedTeam);
        } else {
            // Handle scenario when the team with the given ID is not found
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
    @Override
    public List<TeamDTO> searchByDescriptionForTest(String description) {
        List<Team> teams = teamRepository.findByDescription(description);
        List<TeamDTO> teamDTOs = new ArrayList<>();

        for (Team team : teams) {
            TeamDTO teamDTO = new TeamDTO();
            teamDTO.setName(team.getName());
            teamDTOs.add(teamDTO);
        }

        return teamDTOs;
    }
    public void joinTeam(int teamId) {
        // Retrieve the ID of the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer authenticatedUserId = ((User) authentication.getPrincipal()).getId();

        // Retrieve the team from the database
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (teamOptional.isPresent()) {
            Team team = teamOptional.get();

            User user = userRepository.getById(authenticatedUserId);

            team.getMembers().add(user);

            teamRepository.save(team);
        } else {
            // Handle scenario when the team with the given ID is not found
            throw new IllegalArgumentException("Team not found with ID: " + teamId);
        }
    }


}
