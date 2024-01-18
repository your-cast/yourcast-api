package com.yourcast.api.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.yourcast.api.http.model.request.CreateShowRequest;
import com.yourcast.api.http.model.response.CreateShowResponse;
import com.yourcast.api.service.ShowService;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {

  @Override
  public CreateShowResponse create(CreateShowRequest request, Principal connectedUser) {
    return new CreateShowResponse();
  }
}