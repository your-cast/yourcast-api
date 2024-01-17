package com.yourcast.api.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.yourcast.api.exception.EntityAlreadyExistException;
import com.yourcast.api.exception.EntityNotFoundException;
import com.yourcast.api.hibernate.entity.UserEntity;
import com.yourcast.api.hibernate.entity.TokenEntity;
import com.yourcast.api.hibernate.entity.enums.TokenType;
import com.yourcast.api.hibernate.repository.TokenRepository;
import com.yourcast.api.hibernate.repository.UserRepository;
import com.yourcast.api.http.model.request.LoginRequest;
import com.yourcast.api.http.model.request.SignUpRequest;
import com.yourcast.api.http.model.response.LoginResponse;
import com.yourcast.api.http.model.response.SignUpResponse;
import com.yourcast.api.mapper.UserMapper;
import com.yourcast.api.service.AuthService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final JwtServiceImpl jwtService;
  private final UserRepository userRepository;
  private final TokenRepository tokenRepository;
  private final UserMapper userMapper;
  private final AuthenticationManager authenticationManager;

  @Override
  public SignUpResponse signUp(SignUpRequest request) {
    userRepository.findByEmail(request.getEmail()).ifPresent(entity -> {
      throw new EntityAlreadyExistException("User already exist.");
    });

    UserEntity userEntity = userRepository.save(userMapper.mapCreateUser(request));

    SignUpResponse response = new SignUpResponse();
    response.setEmail(userEntity.getEmail());
    response.setSuccess(true);
    return response;
  }

  @Override
  public LoginResponse login(LoginRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );

    UserEntity userEntity = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new EntityNotFoundException("User not found."));

    String jwtToken = jwtService.generateToken(userEntity);
    String refreshToken = jwtService.generateRefreshToken(userEntity);
    revokeAllUserTokens(userEntity);
    saveToken(userEntity, jwtToken);

    LoginResponse response = new LoginResponse();
    response.setEmail(userEntity.getEmail());
    response.setName(userEntity.getName());
    response.setAccessToken(jwtToken);
    response.setRefreshToken(refreshToken);
    return response;
  }

  private void saveToken(UserEntity user, String jwtToken) {
    TokenEntity token = new TokenEntity();

    token.setUser(user);
    token.setToken(jwtToken);
    token.setTokenType(TokenType.BEARER);
    token.setExpired(false);
    token.setRevoked(false);

    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(UserEntity user) {
    List<TokenEntity> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty()) {
      return;
    }
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }
}