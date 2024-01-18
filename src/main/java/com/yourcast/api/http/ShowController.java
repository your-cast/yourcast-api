package com.yourcast.api.http;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yourcast.api.service.ShowService;
import com.yourcast.api.http.model.request.CreateShowRequest;
import com.yourcast.api.http.model.response.CreateShowResponse;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v2/show", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShowController {

  private final ShowService showService;

  @PostMapping
  public CreateShowResponse createShow(@RequestBody CreateShowRequest request, Principal connectedUser) {
    log.info("Income request for create show: {}", request);
    return showService.create(request, connectedUser);
  }
}