package com.yourcast.api.unit.service;

import com.yourcast.api.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.springframework.security.authentication.AuthenticationManager;

import com.yourcast.api.http.model.request.SignUpRequest;
import com.yourcast.api.http.model.response.SignUpResponse;
import com.yourcast.api.hibernate.entity.UserEntity;
import com.yourcast.api.hibernate.repository.UserRepository;
import com.yourcast.api.mapper.UserMapper;
import com.yourcast.api.exception.EntityAlreadyExistException;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserMapper userMapper;

  @Mock
  private AuthenticationManager authenticationManager;

  @InjectMocks
  private AuthServiceImpl authService;

  @Test
  public void signUpTest_WhenUserDoesNotExist() {
    // Given
    SignUpRequest signUpRequest = new SignUpRequest();
    signUpRequest.setEmail("test@test.com");
    signUpRequest.setName("testName");
    signUpRequest.setPassword("123456");

    when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());

    UserEntity mockUserEntity = new UserEntity();
    mockUserEntity.setEmail("test@test.com");

    when(userMapper.mapCreateUser(any(SignUpRequest.class))).thenReturn(mockUserEntity);
    when(userRepository.save(any(UserEntity.class))).thenReturn(mockUserEntity);

    // When
    SignUpResponse signUpResponse = authService.signUp(signUpRequest);

    // Then
    assert ("test@test.com".equals(signUpResponse.getEmail()));
    assert (signUpResponse.getSuccess());
  }

  @Test
  public void signUpTest_WhenUserAlreadyExist() {
    // Given
    SignUpRequest signUpRequest = new SignUpRequest();
    signUpRequest.setEmail("test@test.com");
    signUpRequest.setName("testName");
    signUpRequest.setPassword("123456");

    UserEntity mockUserEntity = new UserEntity();
    mockUserEntity.setEmail("test@test.com");

    when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(mockUserEntity));

    // When
    // Then
    assertThrows(EntityAlreadyExistException.class, () -> {
      authService.signUp(signUpRequest);
    });
  }
}