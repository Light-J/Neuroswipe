package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.UserRating;

import java.util.List;

public interface UserRatingRepository {
	void storeUserRatings(UserRating rating);
	List<UserRating> getAll();
	List<UserRating> getUserRatings(String userEmail);
	Integer getNumberOfRatingsForUser(String userEmail);
	Integer getNumberOfTimesUserRatedForResponse(Long userId, Integer response);
}
