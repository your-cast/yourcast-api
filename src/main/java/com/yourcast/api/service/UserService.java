package com.yourcast.api.service;

import com.yourcast.api.http.model.response.ProfileResponse;
import com.yourcast.api.http.model.request.ChangePasswordRequest;

import java.security.Principal;

public interface UserService {

  ProfileResponse changePassword(ChangePasswordRequest request, Principal connectedUser);

  ProfileResponse profile(Principal connectedUser);
}
