package com.yourcast.api.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yourcast.api.exception.EntityNotFoundException;
import com.yourcast.api.hibernate.entity.UserEntity;
import com.yourcast.api.hibernate.repository.UserRepository;
import com.yourcast.api.http.model.request.ChangePasswordRequest;
import com.yourcast.api.http.model.response.ProfileResponse;
import com.yourcast.api.service.UserService;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public ProfileResponse changePassword(ChangePasswordRequest request, Principal connectedUser) {
    UserEntity user = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

    // check if the current password is correct
    if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
      throw new IllegalStateException("Wrong password");
    }
    // check if the two new passwords are the same
    if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
      throw new IllegalStateException("Password are not the same");
    }

    UserEntity userEntity = userRepository.findByEmail(user.getUsername())
        .orElseThrow(() -> new EntityNotFoundException("User not found."));

    // update the password
    userEntity.setPassword(passwordEncoder.encode(request.getNewPassword()));

    // save the new password
    userRepository.save(userEntity);

    ProfileResponse response = new ProfileResponse();
    response.setName(userEntity.getName());
    response.setEmail(userEntity.getEmail());
    response.setSystemId(userEntity.getSystemId());
    response.setRole(userEntity.getRole().name());
    return response;
  }
  @Override
  public ProfileResponse profile(Principal connectedUser) {
    UserEntity user = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

    UserEntity userEntity = userRepository.findByEmail(user.getUsername())
        .orElseThrow(() -> new EntityNotFoundException("User not found."));

    ProfileResponse response = new ProfileResponse();
    response.setName(userEntity.getName());
    response.setEmail(userEntity.getEmail());
    response.setSystemId(userEntity.getSystemId());
    response.setRole(userEntity.getRole().name());
    return response;
  }
}
