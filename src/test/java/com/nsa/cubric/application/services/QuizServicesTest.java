package com.nsa.cubric.application.services;

import com.nsa.cubric.application.controllers.AccountDTO;
import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.repositories.AccountRepositoryStatic;
import com.nsa.cubric.application.services.registrationUtils.EmailExistsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.BDDMockito.given;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class QuizServicesTest {

    @Autowired
    QuizServicesStatic quizServices;

    @Test
    public void checkThatImage1isMarkedCorrectly() throws Exception{
        HashMap<Integer, Boolean> answers = new HashMap<>();
        answers.put(1, false);
        Long score = quizServices.markUserResults(answers);
        assertEquals(1L, score.longValue());
    }

    @Test
    public void checkThatImage1isMarkedIncorrectly() throws Exception{
        HashMap<Integer, Boolean> answers = new HashMap<>();
        answers.put(1, true);
        Long score = quizServices.markUserResults(answers);
        assertEquals(0L, score.longValue());
    }
}
