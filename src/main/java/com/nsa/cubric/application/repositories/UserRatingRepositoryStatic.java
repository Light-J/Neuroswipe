package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.UserRating;

import java.util.List;

public interface UserRatingRepositoryStatic {
	public void storeUserRatings(UserRating rating);

	public List<UserRating> getAll();

	public List<UserRating> getUserRatings(String userEmail);
}
