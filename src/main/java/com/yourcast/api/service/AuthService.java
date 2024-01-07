package com.yourcast.api.service;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.yourcast.api.exception.EntityNotFoundException;
import com.yourcast.api.exception.EntityAlreadyExistException;
import com.yourcast.api.hibernate.entity.UserEntity;
import com.yourcast.api.hibernate.repository.UsersRepository;
import com.yourcast.api.http.model.request.LoginRequest;
import com.yourcast.api.http.model.response.LoginResponse;
import com.yourcast.api.http.model.response.ProfileResponse;
import com.yourcast.api.http.model.request.SignUpRequest;
import com.yourcast.api.http.model.response.SignUpResponse;
import com.yourcast.api.mapper.UserMapper;
import com.yourcast.api.util.JwtTokenUtil;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final JwtTokenUtil jwtTokenUtil;
  private final UsersRepository usersRepository;
  private final UserMapper userMapper;

  public SignUpResponse signUp(SignUpRequest request) {
    usersRepository.findByEmail(request.getEmail()).ifPresent(entity -> {
      throw new EntityAlreadyExistException("User already exist.");
    });

    UserEntity userEntity = usersRepository.save(userMapper.mapCreateUser(request));

    SignUpResponse response = new SignUpResponse();
    response.setEmail(userEntity.getEmail());
    response.setSuccess(true);
    return response;
  }

  public LoginResponse login(LoginRequest request) {
    UserEntity userEntity = usersRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new EntityNotFoundException("User not found."));

    LoginResponse response = new LoginResponse();
    response.setEmail(userEntity.getEmail());
    response.setName(userEntity.getName());
    response.setToken(jwtTokenUtil.generateAccessToken(userEntity));
    return response;
  }

  public ProfileResponse profile(HttpHeaders headers) {
    String header = headers.getFirst(HttpHeaders.AUTHORIZATION);
    if (header == null) {
      throw new EntityNotFoundException("User not found.");
    }
    String token = header.split(" ")[1].trim();
    String userEmail = jwtTokenUtil.extractEmail(token);
    UserEntity userEntity = usersRepository.findByEmail(userEmail)
        .orElseThrow(() -> new EntityNotFoundException("User not found."));

    ProfileResponse response = new ProfileResponse();
    response.setName(userEntity.getName());
    return response;
  }
}