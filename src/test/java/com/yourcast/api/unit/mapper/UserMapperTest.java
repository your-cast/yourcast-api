package com.yourcast.api.unit.mapper;

import com.yourcast.api.http.model.request.SignUpRequest;
import com.yourcast.api.hibernate.entity.UserEntity;
import com.yourcast.api.hibernate.entity.enums.UserRole;
import com.yourcast.api.http.model.response.ProfileResponse;
import com.yourcast.api.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserMapperTest {

  UserMapper mapper = new UserMapper() {

    @Override
    public UserEntity mapCreateUser(SignUpRequest request) {
      return null;
    }

    @Override
    public ProfileResponse mapFromEntity(UserEntity userEntity) {
      return null;
    }
  };

  @Test
  public void testEncodePassword() {
    SignUpRequest request = new SignUpRequest();
    request.setPassword("password");
    String encodedPassword = mapper.encodePassword(request);
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    assertTrue(encoder.matches("password", encodedPassword));
  }

  @Test
  public void testGenerateSystemId() {
    assertEquals("YCID7K0D", mapper.generateSystemId());
  }

  @Test
  public void testGenerateDefaultRole() {
    assertEquals(UserRole.USER, mapper.generateDefaultRole());
  }
}