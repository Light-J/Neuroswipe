package com.nsa.cubric.application.controllers.API;

import com.nsa.cubric.application.services.QuizServicesStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "quiz")
public class QuizApi {

    @Autowired
    QuizServicesStatic quizServices;

    @PostMapping(value = "/mark")
    public Long markAnswers(@RequestBody String answers){
        HashMap<Integer, Boolean> answersDict = new HashMap<>();
        String[] allAnswers = answers.split("&");
        for (String item:allAnswers){
            String[] curItem = item.split("=");
            answersDict.put(Integer.parseInt(curItem[0]), Boolean.parseBoolean(curItem[1]));
        }

        Long score = quizServices.markUserResults(answersDict);



        return score;
    }

}
