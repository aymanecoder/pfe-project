package com.group8.projectpfe.services.Impl;

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


    @Override
    public UserDetailsService userDetailsService(){
        return username -> userRepository.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("User not Found"));
    }

}
