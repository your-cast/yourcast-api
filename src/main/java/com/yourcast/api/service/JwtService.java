package com.yourcast.api.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.lang.Function;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {

  String extractUsername(String token);

  <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

  String generateToken(UserDetails userDetails);

  String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

  String generateRefreshToken(UserDetails userDetails);

  boolean isTokenValid(String token, UserDetails userDetails);
}
