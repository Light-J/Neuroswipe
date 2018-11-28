package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.PracticeImage;

import java.util.HashMap;
import java.util.List;

public interface QuizServicesStatic {
    List<PracticeImage> getQuizImages();
    Long markUserResults(HashMap<Integer, Boolean> answers);

}
