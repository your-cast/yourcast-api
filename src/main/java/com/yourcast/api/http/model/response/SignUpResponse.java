package com.yourcast.api.http.model.response;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class SignUpResponse {

  @JsonProperty(value = "email")
  private String email;

  @JsonProperty(value = "success")
  private Boolean success;
}
