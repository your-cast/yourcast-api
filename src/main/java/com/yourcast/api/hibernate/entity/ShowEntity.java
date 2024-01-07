package com.yourcast.api.hibernate.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;

import com.yourcast.api.hibernate.entity.enums.ShowFormat;
import com.yourcast.api.hibernate.entity.enums.ShowStatus;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
@Table(name = "shows")
public class ShowEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "artwork")
  private String artwork;

  @Column(name = "format")
  @Enumerated(EnumType.STRING)
  private ShowFormat format;

  @Column(name = "timezone")
  private String timezone;

  @Column(name = "language")
  private String language;

  @Column(name = "explicit")
  private Boolean explicit;

  @Column(name = "category")
  private String category;

  @Column(name = "tags")
  private String tags;

  @Column(name = "author")
  private String author;

  @Column(name = "podcast_owner")
  private String podcastOwner;

  @Column(name = "email_owner")
  private String emailOwner;

  @Column(name = "copyright")
  private String copyright;

  @Column(name = "token")
  private String token;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private ShowStatus status;

  @OneToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private UserEntity userId;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private ZonedDateTime createdAt = ZonedDateTime.now();

  @UpdateTimestamp
  @Column(name = "updated_at")
  private ZonedDateTime updatedAt = ZonedDateTime.now();
}