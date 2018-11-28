package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.UserRating;
import com.nsa.cubric.application.repositories.UserRatingRepositoryStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRatingService implements UserRatingServiceStatic {

	private UserRatingRepositoryStatic ratingRepository;

	@Autowired
	public UserRatingService(UserRatingRepositoryStatic aRepo) {
		ratingRepository = aRepo;
	}

	@Override
	public void storeUserRatings(UserRating rating) {
		ratingRepository.storeUserRatings(rating);
	}

	@Override
	public List<UserRating> getAll() {
		return ratingRepository.getAll();
	}

	@Override
	public List<UserRating> getUserRatings(String userProfileId) {
		return ratingRepository.getUserRatings(userProfileId);
	}
}
