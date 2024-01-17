package com.yourcast.api.hibernate.entity;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.yourcast.api.hibernate.entity.enums.UserRole;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "system_id")
  private String systemId;

  @Column(name = "email_verified_at")
  private ZonedDateTime emailVerifiedAt;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private UserRole role;

  @OneToMany(mappedBy = "user")
  private List<TokenEntity> tokens;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private ZonedDateTime createdAt = ZonedDateTime.now();

  @UpdateTimestamp
  @Column(name = "updated_at")
  private ZonedDateTime updatedAt = ZonedDateTime.now();

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}