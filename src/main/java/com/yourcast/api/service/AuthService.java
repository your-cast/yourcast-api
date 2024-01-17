package com.yourcast.api.service;

import com.yourcast.api.http.model.request.LoginRequest;
import com.yourcast.api.http.model.response.LoginResponse;
import com.yourcast.api.http.model.request.SignUpRequest;
import com.yourcast.api.http.model.response.SignUpResponse;

public interface AuthService {

  SignUpResponse signUp(SignUpRequest request);

  LoginResponse login(LoginRequest request);
}