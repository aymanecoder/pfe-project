package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.SportifDTO;

import java.util.List;

public interface SportifService {
    List<SportifDTO> getSportifs();

    SportifDTO getSportifById(Long sportifId);
}
