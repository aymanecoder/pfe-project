package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.ProgrammeDTO;
import java.util.List;
import java.util.Optional;

public interface ProgrammeService {
    List<ProgrammeDTO> getAllProgrammes();

    Optional<ProgrammeDTO> getProgrammeById(Long id);

    ProgrammeDTO createProgramme(ProgrammeDTO programmeDetails);

    ProgrammeDTO updateProgramme(Long id, ProgrammeDTO updatedProgrammeDetails);

    void deleteProgramme(Long id);

    List<ProgrammeDTO> searchByTitle(String title);
    List<ProgrammeDTO> getProgramsByTypeProgram(String typeProgram);

    // Other service methods can be declared here
}
