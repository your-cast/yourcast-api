package com.yourcast.api.http.model.request;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

@Data
public class ChangePasswordRequest {

  @NotNull
  @JsonProperty(value = "currentPassword")
  private String currentPassword;

  @NotNull
  @JsonProperty(value = "newPassword")
  private String newPassword;

  @NotNull
  @JsonProperty(value = "confirmationPassword")
  private String confirmationPassword;
}