package com.example.library.service.imple;

import com.example.library.model.Image;
import com.example.library.repository.ImageRepository;
import com.example.library.service.ImageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImple implements ImageService {

    private final ImageRepository imageRepository;

    public ImageServiceImple(ImageRepository imageRepository)
    {
        this.imageRepository=imageRepository;
    }

//    @Override
//    public Optional<Image> findProductImages(Long id) {
//        return Optional.empty();
//    }

    @Override
    public List<Image> findProductImages(long id) {
        return imageRepository.findImageBy(id);
    }



    @Override
    public List<Image> findAll() {
        return imageRepository.findAll();
    }
}
