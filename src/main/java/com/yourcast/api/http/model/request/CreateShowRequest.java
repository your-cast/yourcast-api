package com.yourcast.api.http.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateShowRequest {

  @NotNull
  @Email
  @JsonProperty(value = "email")
  private String email;

  @NotNull
  @JsonProperty(value = "password")
  private String password;
}