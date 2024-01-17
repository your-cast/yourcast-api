package com.yourcast.api.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yourcast.api.hibernate.entity.TokenEntity;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

  @Query(value = "SELECT t FROM TokenEntity t "
      + "INNER JOIN UserEntity u ON t.user.id = u.id "
      + "WHERE u.id = :id AND (t.expired = false OR t.revoked = false)")
  List<TokenEntity> findAllValidTokenByUser(Long id);

  Optional<TokenEntity> findByToken(String token);
}
