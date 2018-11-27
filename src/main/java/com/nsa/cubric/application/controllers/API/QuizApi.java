package com.nsa.cubric.application.controllers.API;

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

    @PostMapping(value = "/mark")
    public String markAnswers(@RequestBody String answers){
        HashMap<Integer, Boolean> answersDict = new HashMap<>();
        String[] allAnswers = answers.split("&");
        for (String item:allAnswers){
            String[] curItem = item.split("=");
            answersDict.put(Integer.parseInt(curItem[0]), Boolean.parseBoolean(curItem[1]));

        }

        for(Map.Entry<Integer, Boolean> entry: answersDict.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }



        return "10";
    }

}
