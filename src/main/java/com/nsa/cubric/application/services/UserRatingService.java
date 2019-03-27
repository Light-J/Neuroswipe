package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.UserRating;

import java.util.List;

public interface UserRatingService {
	boolean storeUserRatings(UserRating rating);
	List<UserRating> getAll();
	List<UserRating> getUserRatings(String userEmail);
	Integer getNumberOfRatingsForUser();
	Integer getNumberOfTimesUserRatedGood();
	Integer getNumberOfTimesUserRatedBad();

}
