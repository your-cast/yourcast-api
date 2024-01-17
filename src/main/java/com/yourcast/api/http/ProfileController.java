package com.yourcast.api.http;

import com.yourcast.api.http.model.request.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yourcast.api.http.model.response.ProfileResponse;
import com.yourcast.api.service.UserService;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v2/profile", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {

  private final UserService userService;

  @GetMapping
  public ProfileResponse userProfile(Principal connectedUser) {
    log.info("Income request for profile user.");
    return userService.profile(connectedUser);
  }

  @PatchMapping
  public ProfileResponse changePassword(
      @RequestBody ChangePasswordRequest request,
      Principal connectedUser
  ) {
    log.info("Income request for update password user.");
    return userService.changePassword(request, connectedUser);
  }
}