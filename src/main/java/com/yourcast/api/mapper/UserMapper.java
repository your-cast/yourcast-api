package com.yourcast.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.yourcast.api.hibernate.entity.UserEntity;
import com.yourcast.api.http.model.request.SignUpRequest;
import com.yourcast.api.hibernate.entity.enums.UserRole;
import com.yourcast.api.http.model.response.ProfileResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "systemId", expression = "java(generateSystemId())")
  @Mapping(target = "role", expression = "java(generateDefaultRole())")
  @Mapping(target = "password", source = "request", qualifiedByName = "encodePassword")
  @Mapping(target = "emailVerifiedAt", ignore = true)
  @Mapping(target = "authorities", ignore = true)
  @Mapping(target = "tokens", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  UserEntity mapCreateUser(SignUpRequest request);

  ProfileResponse mapFromEntity(UserEntity userEntity);

  @Named("encodePassword")
  default String encodePassword(SignUpRequest request) {
    return new BCryptPasswordEncoder().encode(request.getPassword());
  }

  default String generateSystemId() {
    return "YCID7K0D";
  }

  default UserRole generateDefaultRole() {
    return UserRole.USER;
  }
}
