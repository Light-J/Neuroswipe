package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.PracticeScan;

import java.util.HashMap;
import java.util.List;

public interface QuizServicesStatic {
    List<PracticeScan> getQuizImages();
    Long markUserResults(HashMap<Integer, Boolean> answers);

}
