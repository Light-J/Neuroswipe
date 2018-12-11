package com.nsa.cubric.application.controllers.API;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
