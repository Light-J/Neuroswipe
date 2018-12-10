package com.nsa.cubric.application.controllers.API;

import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.domain.UserRating;
import com.nsa.cubric.application.services.AccountServiceStatic;
import com.nsa.cubric.application.services.LoggedUserService;
import com.nsa.cubric.application.services.UserRatingServiceStatic;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	LoggedUserService loggedUserService;

	/**
	 * This method is used to accepted and store the decision the user has made regarding
	 * the scan.
	 *
	 * @param goodBrain boolean whether the user indicated that the scan was "good" or
	 * not
	 * @param scanId ID of the scan that the decision was made for
	 * @return json object with success attribute and error message if applicable
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "application/json")
	public Boolean storeDecision(@RequestParam("scanId") Integer scanId,
                                 @RequestParam("goodBrain") Boolean goodBrain) {


        Account loggedInUser = accountService.getAccountByEmail(loggedUserService.getUsername());

		UserRating rating = new UserRating();
		rating.setUserProfileId(loggedInUser.getId());
		rating.setScanId(scanId);
		rating.setResponse(goodBrain);
		ratingService.storeUserRatings(rating);
		return true;
	}
}