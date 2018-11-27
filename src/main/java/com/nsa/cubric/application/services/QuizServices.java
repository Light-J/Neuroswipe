package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.PracticeImage;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
@Service
public class QuizServices implements QuizServicesStatic{

    @Override
    public List<PracticeImage> getQuizImages(){
        List<PracticeImage> images = Arrays.asList(
                new PracticeImage(1, "1.jpg", true),
                new PracticeImage(2, "2.jpg", true),
                new PracticeImage(3, "3.jpg", true),
                new PracticeImage(4, "4.jpg", true),
                new PracticeImage(5, "5.jpg", true),
                new PracticeImage(6, "6.jpg", true),
                new PracticeImage(7, "7.jpg", true),
                new PracticeImage(8, "8.jpg", true),
                new PracticeImage(9, "9.jpg", true),
                new PracticeImage(10, "10.jpg", true));
        return images;
    }
}
