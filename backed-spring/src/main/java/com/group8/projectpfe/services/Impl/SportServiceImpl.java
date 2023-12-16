package com.group8.projectpfe.services.Impl;

import com.group8.projectpfe.domain.dto.SportifDTO;
import com.group8.projectpfe.domain.dto.SportDTO;
import com.group8.projectpfe.entities.Sport;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.mappers.impl.SportifMapper;
import com.group8.projectpfe.mappers.impl.SportMapperImpl;
import com.group8.projectpfe.repositories.SportRepository;
import com.group8.projectpfe.services.SportService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SportServiceImpl implements SportService {

    private final SportRepository sportRepository;
    private final SportMapperImpl sportMapper;

    private final ModelMapper modelMapper;

    @Override
    public List<SportDTO> getAllSports() {
        List<Sport> sports = sportRepository.findAll();
        return sports.stream().map(sportMapper::mapTo).collect(Collectors.toList());
    }

    @Override
    public Optional<SportDTO> getSportById(int id) {
        Optional<Sport> sportOptional = sportRepository.findById(id);
        return sportOptional.map(sportMapper::mapTo);
    }

    @Override
    public SportDTO createSport(SportDTO sportDetails) {
        Sport sportToCreate = sportMapper.mapFrom(sportDetails);
        Sport savedSport = sportRepository.save(sportToCreate);
        return sportMapper.mapTo(savedSport);
    }

    @Override
    public SportDTO updateSport(int id, SportDTO sportDTO) {
        Sport existingSport = sportRepository.findById(id).orElse(null);

        if (existingSport != null) {
            modelMapper.map(sportDTO, existingSport);
            sportRepository.save(existingSport);
        } else {
            throw new EntityNotFoundException("Sport not found with ID: " + sportDTO.getId());
        }
        return sportDTO;
    }

    @Override
    public void deleteSport(int id) {
        sportRepository.deleteById(id);
    }



}
