package com.yourcast.api.http.model.request;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignUpRequest {

  @NotNull
  @Email
  @JsonProperty(value = "email")
  private String email;

  @NotNull
  @Length(min = 5, max = 128)
  @JsonProperty(value = "name")
  private String name;

  @NotNull
  @Length(min = 5, max = 128)
  @JsonProperty(value = "password")
  private String password;
}