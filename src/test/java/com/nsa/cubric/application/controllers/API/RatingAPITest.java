package com.nsa.cubric.application.controllers.API;

import com.nsa.cubric.application.domain.UserRating;
import com.nsa.cubric.application.services.UserRatingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class RatingAPITest {

	UserRating userRating;

	@Autowired
	MockMvc mvc;

	@MockBean
    UserRatingService userRatingService;

	@Before
	public void setupBasicUserRatingDetails() {
		userRating = new UserRating();
		userRating.setUserProfileId(1);
		userRating.setImageId(1);
		userRating.setResponse(true);
	}

	@Test
	public void storeValidDecisionTest() throws Exception {

		this.mvc.perform(post("/ratings/save").param("userProfileId", userRating.getUserProfileId().toString())
				.param("imageId", userRating.getImageId().toString())
				.param("goodBrain", userRating.getResponse().toString())).andExpect(status().isOk());
	}

	@Test
	public void storeInvalidDecisionTest() throws Exception {

		this.mvc.perform(post("/ratings/save").param("userProfileId", userRating.getUserProfileId().toString())
				.param("imageId", userRating.getImageId().toString()).param("goodBrain", "goodBrain"))
				.andExpect(status().is4xxClientError());
	}

}
