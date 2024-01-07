package com.yourcast.api.http.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponse {

  @JsonProperty(value = "email")
  private String email;

  @JsonProperty(value = "name")
  private String name;

  @JsonProperty(value = "token")
  private String token;
}
