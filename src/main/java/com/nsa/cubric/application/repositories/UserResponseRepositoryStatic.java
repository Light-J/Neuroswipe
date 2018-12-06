package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.UserResponse;

import java.util.List;

public interface UserResponseRepositoryStatic {
	public void storeUserResponse(UserResponse response);

	public List<UserResponse> getAll();

	public List<UserResponse> getUserResponses(String userProfileId);
}
