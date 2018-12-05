package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.UserResponse;
import com.nsa.cubric.application.repositories.UserResponseRepositoryStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserResponseService implements UserResponseServiceStatic {

	private UserResponseRepositoryStatic responseRepository;

	@Autowired
	public UserResponseService(UserResponseRepositoryStatic aRepo) {
		responseRepository = aRepo;
	}

	@Override
	public void storeUserResponse(UserResponse response) {
		responseRepository.storeUserResponse(response);
	}

	@Override
	public List<UserResponse> getAll() {
		return responseRepository.getAll();
	}

	@Override
	public List<UserResponse> getUserResponses(String userProfileId) {
		return responseRepository.getUserResponses(userProfileId);
	}
}
