package com.yourcast.api.service;

import lombok.RequiredArgsConstructor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.yourcast.api.hibernate.entity.TokenEntity;
import com.yourcast.api.hibernate.repository.TokenRepository;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

  private final TokenRepository tokenRepository;

  @Override
  public void logout(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication
  ) {
    String authHeader = request.getHeader(AUTHORIZATION);
    String jwt;
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return;
    }
    jwt = authHeader.substring(7);
    TokenEntity tokenEntity = tokenRepository.findByToken(jwt).orElse(null);
    if (tokenEntity != null) {
      tokenEntity.setExpired(true);
      tokenEntity.setRevoked(true);
      tokenRepository.save(tokenEntity);
      SecurityContextHolder.clearContext();
    }
  }
}
