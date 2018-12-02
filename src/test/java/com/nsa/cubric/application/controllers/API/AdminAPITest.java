package com.nsa.cubric.application.controllers.API;


import com.nsa.cubric.application.services.AdminServicesStatic;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AdminAPITest {

    @Autowired
    MockMvc mvc;

    @MockBean
    AdminServicesStatic adminServices;

    @Test
    public void removeUserValidTest() throws Exception{
        given(adminServices.removeUser(1)).willReturn(true);

        mvc.perform(post("/removeUser")
                .param("user_to_remove", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void removeUserInvalidTest() throws Exception{
        given(adminServices.removeUser(2)).willReturn(false);

        mvc.perform(post("/removeUser")
                .param("user_to_remove", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    public void removeUserResponses() throws Exception{
        given(adminServices.removeUserResponses(2)).willReturn(3);


        mvc.perform(post("/removeUserResponses")
                .param("user_to_remove_responses", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string("3"));


    }

}
