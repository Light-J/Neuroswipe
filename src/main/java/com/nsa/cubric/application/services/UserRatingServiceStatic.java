package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.UserRating;

import java.util.List;

public interface UserRatingServiceStatic {
	public boolean storeUserRatings(UserRating rating);
	public List<UserRating> getAll();
	public List<UserRating> getUserRatings(String userEmail);
}
