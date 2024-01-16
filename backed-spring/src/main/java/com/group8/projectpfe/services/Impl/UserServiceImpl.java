package com.group8.projectpfe.services.Impl;

import com.group8.projectpfe.domain.dto.GroupDto;
import com.group8.projectpfe.entities.Group;
import com.group8.projectpfe.entities.User;
import com.group8.projectpfe.mappers.impl.GroupMapper;
import com.group8.projectpfe.repositories.UserRepository;
import com.group8.projectpfe.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GroupMapper groupMapper;

    @Override
    public UserDetailsService userDetailsService(){
        return username -> userRepository.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("User not Found"));
    }
    @Override
    public User joinGroup(int userId, GroupDto groupDto) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            // Convert GroupDto to Group entity using your mapper or constructor
            Group group = groupMapper.mapFrom(groupDto);

            // Set the group on the user
            user.setGroup(group);

            // Save the user
            return userRepository.save(user);
        } else {
            return null; // User not found
        }
    }


}
