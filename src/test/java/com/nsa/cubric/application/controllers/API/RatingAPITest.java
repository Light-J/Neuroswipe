package com.nsa.cubric.application.controllers.API;

import com.nsa.cubric.application.controllers.AccountDTO;
import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.domain.UserRating;
import com.nsa.cubric.application.services.AccountServiceStatic;
import com.nsa.cubric.application.services.LoggedUserService;
import com.nsa.cubric.application.services.UserRatingService;
import com.nsa.cubric.application.services.registrationUtils.EmailExistsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
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
		userRating.setUserProfileId(1L);
		userRating.setScanId(1);
		userRating.setResponse(true);
	}

	@Test
	public void storeValidDecisionTest() throws Exception {
		//goThroughLogin();

		this.mvc.perform(post("/ratings/save").param("userProfileId", userRating.getUserProfileId().toString())
				.param("imageId", userRating.getScanId().toString())
				.param("goodBrain", userRating.getResponse().toString())).andExpect(status().isOk());
	}

	@Test
	public void storeInvalidDecisionTest() throws Exception {
		//goThroughLogin();


		this.mvc.perform(post("/ratings/save").param("userProfileId", userRating.getUserProfileId().toString())
				.param("imageId", userRating.getScanId().toString()).param("goodBrain", "goodBrain"))
				.andExpect(status().is4xxClientError());
	}

	AccountDTO testAccount;

	@Before
	public void setupBasicAccountDTO() {
		testAccount = new AccountDTO();
		testAccount.setEmail("test@nsa.com");
		testAccount.setPassword("Password");
		testAccount.setMatchingPassword("Password");
	}

	@Autowired
	private AccountServiceStatic accountService;

	public void goThroughLogin(){
		Account validAccount = new Account(1L, "test@nsa.com", "Password", "user");

		try {
			accountService.registerNewUserAccount(testAccount);
		} catch (EmailExistsException e) {
			e.printStackTrace();
		}

		try {
			this.mvc.perform(post("/login")
					.param("username", "test@nsa.com")
					.param("password", "Password"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
