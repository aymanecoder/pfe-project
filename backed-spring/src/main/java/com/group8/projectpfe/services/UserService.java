package com.group8.projectpfe.services;

import com.group8.projectpfe.domain.dto.GroupDto;
import com.group8.projectpfe.entities.Group;
import com.group8.projectpfe.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


public interface UserService {
    UserDetailsService userDetailsService();
    User joinGroup(int userId, GroupDto group);
}
