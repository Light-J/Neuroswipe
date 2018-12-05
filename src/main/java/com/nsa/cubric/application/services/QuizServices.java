package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.PracticeScan;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuizServices implements QuizServicesStatic{

    private List<PracticeScan> images = Arrays.asList(
            new PracticeScan(1, "1.jpg", "1.jpg", "1.jpg", false),
            new PracticeScan(2, "2.jpg", "2.jpg", "2.jpg", false),
            new PracticeScan(3, "3.jpg", "3.jpg", "3.jpg", false),
            new PracticeScan(4, "4.jpg", "4.jpg", "4.jpg", true),
            new PracticeScan(5, "5.jpg", "5.jpg", "5.jpg", true),
            new PracticeScan(6, "6.jpg", "6.jpg", "6.jpg", true),
            new PracticeScan(7, "7.jpg", "7.jpg", "7.jpg", true),
            new PracticeScan(8, "8.jpg", "8.jpg", "8.jpg", true),
            new PracticeScan(9, "9.jpg", "9.jpg", "9.jpg", true),
            new PracticeScan(10, "10.jpg", "10.jpg", "10.jpg", true));

    @Override
    public List<PracticeScan> getQuizImages(){
        return images;
    }

    @Override
    public Long markUserResults(HashMap<Integer, Boolean> answers){
        Long score = 0L;

        for(Map.Entry<Integer, Boolean> entry: answers.entrySet()) {
            System.out.println(entry.getKey().toString() + " " + entry.getValue().toString());
            score = score + markAnswer(entry.getKey(), entry.getValue());
            System.out.println(score);
        }
        System.out.println(score);
        return score;
    }

    private Long markAnswer(Integer question, Boolean answer){
        if(answer == images.get(question).getImageCorrect()){
            return 1L;
        } else {
            return 0L;
        }
    }
}
