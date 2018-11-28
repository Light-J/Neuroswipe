package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.Image;

import java.util.List;

public interface ImageRepositoryStatic {
    public Image findById(Long id);
    public void insert(Image image);
    public List<Image> getAll();
}
