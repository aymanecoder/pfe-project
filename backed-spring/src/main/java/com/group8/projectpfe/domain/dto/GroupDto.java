package com.group8.projectpfe.domain.dto;

import com.group8.projectpfe.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GroupDto {

        private Long id;
        private String name;
        private List<SportifDTO> members;

}
