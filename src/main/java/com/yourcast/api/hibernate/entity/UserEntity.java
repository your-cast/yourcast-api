package com.yourcast.api.hibernate.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {

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

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private ZonedDateTime createdAt = ZonedDateTime.now();

  @UpdateTimestamp
  @Column(name = "updated_at")
  private ZonedDateTime updatedAt = ZonedDateTime.now();
}