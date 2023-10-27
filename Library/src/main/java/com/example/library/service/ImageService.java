package com.example.library.service;

import com.example.library.model.Image;

import java.util.List;
import java.util.Optional;

public interface ImageService {

//    Optional<Image> findProductImages(Long id);

    List<Image> findProductImages(long id);

    List<Image>findAll();
}
