package com.group8.projectpfe.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


public interface JwtService {
String generateToken(UserDetails userDetails);
String extractUsername(String token);
boolean isTokenValid(String token, UserDetails userDetails);

}
