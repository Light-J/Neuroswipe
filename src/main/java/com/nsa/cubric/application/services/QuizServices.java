package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.PracticeImage;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
@Service
public class QuizServices implements QuizServicesStatic{

    private List<PracticeImage> images = Arrays.asList(
            new PracticeImage(1, "1.jpg", true),
            new PracticeImage(2, "2.jpg", false),
            new PracticeImage(3, "3.jpg", false),
            new PracticeImage(4, "4.jpg", true),
            new PracticeImage(5, "5.jpg", false),
            new PracticeImage(6, "6.jpg", false),
            new PracticeImage(7, "7.jpg", true),
            new PracticeImage(8, "8.jpg", true),
            new PracticeImage(9, "9.jpg", false),
            new PracticeImage(10, "10.jpg", true));

    @Override
    public List<PracticeImage> getQuizImages(){
        return images;
    }

    @Override
    public Long markUserResults(HashMap<Integer, Boolean> answers){

        return 10L;
    }

    private Long markAnswer(Integer question, Boolean answer){
        if(answer == images.get(question+1).getImageCorrect()){
            return 1L;
        } else {
            return 0L;
        }
    }
}
