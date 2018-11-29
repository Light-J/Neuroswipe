package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.Image;

import java.util.List;

public interface ImageRepositoryStatic {
    public Image findById(Long id);
    public void insert(Image image);
    public void updateKnownGood(Long id, Boolean knownGood);
    public List<Image> getAll();
}
