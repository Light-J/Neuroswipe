package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.Image;

import java.util.List;

public interface ImageServiceStatic {
    Image findById(Long id);
    void insert(Image image);
    void updateKnownGood(Long id, Boolean knownGood);
    List<Image> getAll();
}
