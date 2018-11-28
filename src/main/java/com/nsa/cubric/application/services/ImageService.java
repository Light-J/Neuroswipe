package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.Image;
import com.nsa.cubric.application.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService implements ImageServiceStatic {

    private ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Image findById(Long id){
        return imageRepository.findById(id);
    }

    @Override
    public void insert(Image image){
        imageRepository.insert(image);
    }

    @Override
    public List<Image> getAll(){
        return imageRepository.getAll();
    }
}
