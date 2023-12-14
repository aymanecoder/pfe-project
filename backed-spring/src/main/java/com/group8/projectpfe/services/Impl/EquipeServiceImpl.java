package com.group8.projectpfe.services.Impl;

import com.group8.projectpfe.domain.dto.EquipeDTO;
import com.group8.projectpfe.entities.Equipe;
import com.group8.projectpfe.mappers.impl.EquipeMapper;
import com.group8.projectpfe.repositories.EquipeRepository;
import com.group8.projectpfe.services.EquipeService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipeServiceImpl implements EquipeService {
    private final EquipeRepository equipeRepository;
    private final EquipeMapper equipeMapper;
    private final ModelMapper modelMapper;

    public EquipeServiceImpl(EquipeRepository equipeRepository, EquipeMapper equipeMapper, ModelMapper modelMapper) {
        this.equipeRepository = equipeRepository;
        this.equipeMapper = equipeMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public EquipeDTO createEquipe(EquipeDTO equipeDTO) {
        Equipe equipe = equipeMapper.mapFrom(equipeDTO);
        Equipe savedEquipe = equipeRepository.save(equipe);
        return equipeMapper.mapTo(savedEquipe);
    }

    @Override
    public EquipeDTO getEquipeById(Integer id) {
        Equipe equipe = equipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipe not found with ID: " + id));
        return equipeMapper.mapTo(equipe);
    }

    @Override
    public EquipeDTO updateEquipe(Integer id, EquipeDTO equipeDTO) {
        Equipe existingEquipe = equipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipe not found with ID: " + id));

        // Map the updated data from DTO to the existing equipe entity
        modelMapper.map(equipeDTO, existingEquipe);

        // Save the updated equipe entity
        Equipe updatedEquipe = equipeRepository.save(existingEquipe);
        return equipeMapper.mapTo(updatedEquipe);
    }

    @Override
    public List<EquipeDTO> getAllEquipes() {
        List<Equipe> equipes = equipeRepository.findAll();
        return equipes.stream()
                .map(equipeMapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEquipe(Integer id) {
        if (!equipeRepository.existsById(id)) {
            throw new EntityNotFoundException("Equipe not found with ID: " + id);
        }
        equipeRepository.deleteById(id);
    }
}
