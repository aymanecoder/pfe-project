package com.group8.projectpfe.services.Impl;
import com.group8.projectpfe.domain.dto.ProgrammeDTO;
import com.group8.projectpfe.entities.Programme;
import com.group8.projectpfe.mappers.impl.ProgrammeMapperImpl;
import com.group8.projectpfe.repositories.ProgrammeRepository;
import com.group8.projectpfe.services.ProgrammeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class ProgrammeServiceImpl implements ProgrammeService {


    @Autowired
    private ProgrammeRepository programmeRepository;

    @Autowired
    private ProgrammeMapperImpl programmeMapper;



    @Override
    public List<ProgrammeDTO> getAllProgrammes() {
        List<Programme> programmes = programmeRepository.findAll();
        List<ProgrammeDTO> programmeDTOs = new ArrayList<>();

        for (Programme programme : programmes) {
            programmeDTOs.add(programmeMapper.mapTo(programme));
        }

        return programmeDTOs;
    }

    @Override
    public Optional<ProgrammeDTO> getProgrammeById(Long id) {
        Optional<Programme> programmeOptional = programmeRepository.findById(id);

        if (programmeOptional.isPresent()) {
            Programme programme = programmeOptional.get();
            ProgrammeDTO programmeDTO = programmeMapper.mapTo(programme);
            return Optional.of(programmeDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public ProgrammeDTO createProgramme(ProgrammeDTO programmeDetails) {
        // Mapping ProgrammeDTO to Programme entity
        Programme programmeToCreate = new Programme();
        programmeToCreate.setUrl(programmeDetails.getUrl());
        programmeToCreate.setTitle(programmeDetails.getTitle());
        programmeToCreate.setFichier(programmeDetails.getFichier());

        // Save Programme entity
        Programme savedProgramme = programmeRepository.save(programmeToCreate);

        // Mapping saved Programme entity to ProgrammeDTO
        ProgrammeDTO savedProgrammeDTO = new ProgrammeDTO();
        savedProgrammeDTO.setId(savedProgramme.getId());
        savedProgrammeDTO.setUrl(savedProgramme.getUrl());
        savedProgrammeDTO.setTitle(savedProgramme.getTitle());
        savedProgrammeDTO.setFichier(savedProgramme.getFichier());

        return savedProgrammeDTO;
    }

    @Override
    public ProgrammeDTO updateProgramme(Long id, ProgrammeDTO updatedProgrammeDetails) {
        Optional<Programme> optionalProgramme = programmeRepository.findById(id);

        if (optionalProgramme.isPresent()) {
            Programme existingProgramme = optionalProgramme.get();

            // Update Programme entity with updated details from ProgrammeDTO
            existingProgramme.setUrl(updatedProgrammeDetails.getUrl());
            existingProgramme.setTitle(updatedProgrammeDetails.getTitle());
            existingProgramme.setFichier(updatedProgrammeDetails.getFichier());

            // Save the updated Programme entity
            Programme updatedProgramme = programmeRepository.save(existingProgramme);

            // Map the updated Programme entity to ProgrammeDTO
            return programmeMapper.mapTo(updatedProgramme);
        } else {
            return null;
        }
    }

    @Override
    public void deleteProgramme(Long id) {
        programmeRepository.deleteById(id);
    }

    @Override
    public List<ProgrammeDTO> searchByTitle(String title) {
        List<Programme> programmesByTitle = programmeRepository.findByTitle(title);
        List<ProgrammeDTO> programmeDTOs = new ArrayList<>();

        for (Programme programme : programmesByTitle) {
            ProgrammeDTO programmeDTO = programmeMapper.mapTo(programme);
            programmeDTOs.add(programmeDTO);
        }

        return programmeDTOs;
    }
}


