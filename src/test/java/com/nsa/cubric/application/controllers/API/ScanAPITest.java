package com.nsa.cubric.application.controllers.API;

import com.nsa.cubric.application.domain.UserResponse;
import com.nsa.cubric.application.services.UserResponseService;
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
public class ScanAPITest {

	UserResponse userResponse;

	@Autowired
	MockMvc mvc;

	@MockBean
    UserResponseService userResponseService;

	@Before
	public void setupBasicUserResponse() {
		userResponse = new UserResponse();
		userResponse.setUserProfileId(1L);
		userResponse.setScanId(1);
		userResponse.setResponse(true);
	}

	@Test
	public void storeValidDecisionTest() throws Exception {

		this.mvc.perform(post("/scans/save").param("userProfileId", userResponse.getUserProfileId().toString())
				.param("scanId", userResponse.getScanId().toString())
				.param("goodBrain", userResponse.getResponse().toString())).andExpect(status().isOk());
	}

	@Test
	public void storeInvalidDecisionTest() throws Exception {

		this.mvc.perform(post("/scans/save").param("userProfileId", userResponse.getUserProfileId().toString())
				.param("scanId", userResponse.getScanId().toString()).param("goodBrain", "goodBrain"))
				.andExpect(status().is4xxClientError());
	}

}
