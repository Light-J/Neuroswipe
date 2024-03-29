package com.nsa.cubric.application.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IntroductionControllerControllerTest {

    @Autowired
    private IntroductionController introductionControllerController;

    @Test
    public void loadcontroller() throws Exception{
        assertThat(introductionControllerController).isNotNull();
    }
}
