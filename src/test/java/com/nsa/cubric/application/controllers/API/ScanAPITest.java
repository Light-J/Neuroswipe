package com.nsa.cubric.application.controllers.API;

import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.domain.UserResponse;
import com.nsa.cubric.application.services.AccountServiceStatic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ScanAPITest {

	@Autowired
	MockMvc mvc;

	@Test
	public void moreWorkNeededHERE () {
		System.out.println("Write tests");
	}
}
