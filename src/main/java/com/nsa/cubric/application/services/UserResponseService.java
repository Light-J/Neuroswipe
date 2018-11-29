package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.UserResponse;
import com.nsa.cubric.application.repositories.UserResponseRepositoryStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserResponseService implements UserResponseServiceStatic {

	private UserResponseRepositoryStatic responsesRepository;

	@Autowired
	public UserResponseService(UserResponseRepositoryStatic aRepo) {
		responsesRepository = aRepo;
	}

	@Override
	public void storeUserResponses(UserResponse responses) {
		responsesRepository.storeUserResponses(responses);
	}

	@Override
	public List<UserResponse> getAll() {
		return responsesRepository.getAll();
	}

	@Override
	public List<UserResponse> getUserResponses(String userProfileId) {
		return responsesRepository.getUserResponses(userProfileId);
	}
}
