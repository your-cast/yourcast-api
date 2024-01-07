package com.yourcast.api.hibernate.entity;

import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "followers")
public class FollowerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @OneToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "following_user_id", referencedColumnName = "id")
  private UserEntity followingUser;

  @OneToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "followed_user_id", referencedColumnName = "id")
  private UserEntity followedUser;

  @Column(name = "notifications")
  private String notifications;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private ZonedDateTime createdAt = ZonedDateTime.now();

  @UpdateTimestamp
  @Column(name = "updated_at")
  private ZonedDateTime updatedAt = ZonedDateTime.now();
}