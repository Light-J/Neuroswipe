package com.nsa.cubric.application.controllers.API;

import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.domain.UserRating;
import com.nsa.cubric.application.services.AccountServiceStatic;
import com.nsa.cubric.application.services.UserRatingServiceStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("ratings")
@RestController
public class RatingAPI {

	private UserRatingServiceStatic ratingService;

	@Autowired
	public RatingAPI(UserRatingServiceStatic aRepo) {
		ratingService = aRepo;
	}

	@Autowired
    AccountServiceStatic accountService;

	/**
	 * This method is used to accepted and store the decision the user has made regarding
	 * the image.
	 *
	 * @param goodBrain boolean whether the user indicated that the image was "good" or
	 * not
	 * @param imageId ID of the image that the decision was made for
	 * @return json object with success attribute and error message if applicable
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "application/json")
	public Boolean storeDecision(@RequestParam("imageId") Integer imageId,
                                 @RequestParam("goodBrain") Boolean goodBrain) {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account loggedInUser = accountService.findByEmail(auth.getName());

		UserRating rating = new UserRating();
		rating.setUserProfileId(loggedInUser.getId());
		rating.setScanId(imageId);
		rating.setResponse(goodBrain);
		ratingService.storeUserRatings(rating);
		return true;
	}
}