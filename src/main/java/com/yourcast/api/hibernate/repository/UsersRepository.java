package com.yourcast.api.hibernate.repository;

import jakarta.transaction.Transactional;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.yourcast.api.hibernate.entity.UserEntity;
import org.springframework.data.repository.query.Param;

@Transactional
public interface UsersRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByEmail(@Param("email") String email);
}