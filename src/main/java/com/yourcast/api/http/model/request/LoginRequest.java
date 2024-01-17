package com.yourcast.api.http.model.request;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class LoginRequest {

  @NotNull
  @Email
  @JsonProperty(value = "email")
  private String email;

  @NotNull
  @JsonProperty(value = "password")
  private String password;
}