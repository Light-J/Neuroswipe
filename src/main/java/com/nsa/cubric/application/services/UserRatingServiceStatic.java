package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.UserRating;
import com.nsa.cubric.application.repositories.UserRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRatingServiceStatic implements UserRatingService {

	private UserRatingRepository ratingRepository;

	private LoggedUserService loggedUserService;

	@Autowired
	public UserRatingServiceStatic(UserRatingRepository userRatingRepository, LoggedUserService aloggedUserService) {
		ratingRepository = userRatingRepository;
		loggedUserService = aloggedUserService;
	}


	@Override
	public boolean storeUserRatings(UserRating rating) {
		try{
			ratingRepository.storeUserRatings(rating);
			return true;
		}catch (Exception e){
			return false;
		}

	}

	@Override
	public List<UserRating> getAll() {
		return ratingRepository.getAll();
	}

	@Override
	public List<UserRating> getUserRatings(String userEmail) {
		return ratingRepository.getUserRatings(userEmail);
	}

	@Override
	public Integer getNumberOfRatingsForUser(){
		return ratingRepository.getNumberOfRatingsForUser(loggedUserService.getUsername());
	}
}
