package com.group8.projectpfe.services.Impl;
import com.group8.projectpfe.domain.dto.ProgrammeDTO;
import com.group8.projectpfe.entities.Programme;
import com.group8.projectpfe.entities.TypeProgram;
import com.group8.projectpfe.mappers.impl.ProgrammeMapperImpl;
import com.group8.projectpfe.mappers.impl.SportifMapper;
import com.group8.projectpfe.repositories.ProgrammeRepository;
import com.group8.projectpfe.services.ProgrammeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgrammeServiceImpl implements ProgrammeService {


    private final ProgrammeRepository programmeRepository;

    private final ProgrammeMapperImpl programmeMapper;
    private final ModelMapper modelMapper;



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
        Programme programmeToCreate=new Programme();
        modelMapper.map(programmeDetails, programmeToCreate);
        // Save Programme entity
        Programme savedProgramme = programmeRepository.save(programmeToCreate);

        modelMapper.map(programmeToCreate, programmeDetails);
        return programmeDetails;

    }

    @Override
    public ProgrammeDTO updateProgramme(Long id, ProgrammeDTO updatedProgrammeDetails) {
        Optional<Programme> optionalProgramme = programmeRepository.findById(id);

        if (optionalProgramme.isPresent()) {
            Programme existingProgramme = optionalProgramme.get();
            modelMapper.map(updatedProgrammeDetails, existingProgramme);
            Programme updatedProgramme = programmeRepository.save(existingProgramme);
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

    @Override
    public List<ProgrammeDTO> getProgramsByTypeProgram(String typeProgram) {

        TypeProgram typeProgramEnum = TypeProgram.valueOf(typeProgram);

        List<Programme> programmesByTypeProgram = programmeRepository.findByTypeProgramme(typeProgramEnum);

        return programmesByTypeProgram.stream()
                .map(programmeMapper::mapTo)
                .collect(Collectors.toList());
    }


}


