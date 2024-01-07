package com.yourcast.api.http;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yourcast.api.service.AuthService;
import com.yourcast.api.http.model.request.LoginRequest;
import com.yourcast.api.http.model.request.SignUpRequest;
import com.yourcast.api.http.model.response.LoginResponse;
import com.yourcast.api.http.model.response.ProfileResponse;
import com.yourcast.api.http.model.response.SignUpResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v2/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public LoginResponse userLogin(@Valid @RequestBody LoginRequest request) {
    log.info("Income request for auth user: {}", request.getEmail());
    return authService.login(request);
  }

  @PostMapping("/signup")
  public SignUpResponse userRegister(@Valid @RequestBody SignUpRequest request) {
    log.info("Income request for register user: {}", request.getEmail());
    return authService.signUp(request);
  }

  @GetMapping("/profile")
  public ProfileResponse userProfile(@RequestHeader HttpHeaders headers) {
    return authService.profile(headers);
  }
}