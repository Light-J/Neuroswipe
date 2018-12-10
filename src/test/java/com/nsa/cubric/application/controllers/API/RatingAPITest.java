package com.nsa.cubric.application.controllers.API;

import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.domain.UserRating;
import com.nsa.cubric.application.services.AccountServiceStatic;
import com.nsa.cubric.application.services.UserRatingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RatingAPITest {

	UserRating userRating;

	Account testAccount;


	@Autowired
	MockMvc mvc;

	@MockBean
    UserRatingService userRatingService;

	@MockBean
	AccountServiceStatic accountService;

	@Before
	public void setupBasicUserRatingDetails() {
		testAccount = new Account(1L, "test@user.com", "pass", "user");
		userRating = new UserRating();
		userRating.setUserProfileId(1L);
		userRating.setScanId(1);
		userRating.setResponse(true);

		given(accountService.getAccountByEmail("user@test.com")).willReturn(testAccount);
	}

	@Test
	@WithMockUser(username = "user@test.com", authorities = { "user" })
	public void storeValidDecisionTest() throws Exception {
		given(userRatingService.storeUserRatings(userRating)).willReturn(true);

		this.mvc.perform(post("/ratings/save")
				.param("scanId", userRating.getScanId().toString())
				.param("goodBrain", userRating.getResponse().toString())).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "user@test.com", authorities = { "user" })
	public void storeInvalidDecisionTest() throws Exception {

		this.mvc.perform(post("/ratings/save")
				.param("scanId", userRating.getScanId().toString())
				.param("goodBrain", "goodBrain"))
				.andExpect(status().is4xxClientError());
	}
}
