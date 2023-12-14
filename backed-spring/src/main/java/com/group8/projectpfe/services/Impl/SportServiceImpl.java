package com.group8.projectpfe.services.Impl;

import com.group8.projectpfe.domain.dto.SportDTO;
import com.group8.projectpfe.entities.Sport;
import com.group8.projectpfe.mappers.impl.SportMapper;
import com.group8.projectpfe.repositories.SportRepository;
import com.group8.projectpfe.services.SportService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SportServiceImpl implements SportService {
    private final SportRepository sportRepository;
    private final SportMapper sportMapper;
    private final ModelMapper modelMapper;

    public SportServiceImpl(SportRepository sportRepository, SportMapper sportMapper, ModelMapper modelMapper) {
        this.sportRepository = sportRepository;
        this.sportMapper = sportMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public SportDTO createSport(SportDTO sportDTO) {
        Sport sport = sportMapper.mapFrom(sportDTO);
        Sport savedSport = sportRepository.save(sport);
        return sportMapper.mapTo(savedSport);
    }

    @Override
    public SportDTO getSportById(Integer id) {
        Sport sport = sportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sport not found with ID: " + id));
        return sportMapper.mapTo(sport);
    }

    @Override
    public SportDTO updateSport(Integer id, SportDTO sportDTO) {
        Sport existingSport = sportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sport not found with ID: " + id));

        // Map the updated data from DTO to the existing sport entity
        modelMapper.map(sportDTO, existingSport);

        // Save the updated sport entity
        Sport updatedSport = sportRepository.save(existingSport);
        return sportMapper.mapTo(updatedSport);
    }

    @Override
    public List<SportDTO> getAllSports() {
        // Retrieve a list of Sport entities from the database using the SportRepository
        List<Sport> sports = sportRepository.findAll();

        // Use the Stream API to transform each Sport entity into a SportDTO
        // The map operation applies the sportMapper::mapTo function to each element of the stream
        // The result is a Stream<SportDTO>
        return sports.stream()
                .map(sportMapper::mapTo)
                // Collect the Stream<SportDTO> into a List<SportDTO>
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSport(Integer id) {
        if (!sportRepository.existsById(id)) {
            throw new EntityNotFoundException("Sport not found with ID: " + id);
        }
        sportRepository.deleteById(id);
    }
}
