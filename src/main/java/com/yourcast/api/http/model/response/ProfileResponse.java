package com.yourcast.api.http.model.response;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class ProfileResponse {

  @JsonProperty(value = "name")
  private String name;

  @JsonProperty(value = "email")
  private String email;

  @JsonProperty(value = "systemId")
  private String systemId;

  @JsonProperty(value = "role")
  private String role;
}
