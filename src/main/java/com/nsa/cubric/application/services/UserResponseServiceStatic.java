package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.UserResponse;

import java.util.List;

public interface UserResponseServiceStatic {
	public void storeUserResponses(UserResponse responses);

	public List<UserResponse> getAll();

	public List<UserResponse> getUserResponses(String userProfileId);

}
