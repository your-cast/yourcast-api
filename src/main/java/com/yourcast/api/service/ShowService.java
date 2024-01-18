package com.yourcast.api.service;

import com.yourcast.api.http.model.request.CreateShowRequest;
import com.yourcast.api.http.model.response.CreateShowResponse;

import java.security.Principal;

public interface ShowService {

  CreateShowResponse create(CreateShowRequest request, Principal connectedUser);
}