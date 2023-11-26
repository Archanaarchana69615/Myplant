package com.example.library.service;

import com.example.library.dto.ReviewDto;
import com.example.library.model.Review;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ReviewService {

    List<Review> readReviewByProduct(Long productId);

    Double findRatingByProduct(Long productId);


    Review save(ReviewDto reviewDto, String email, Long productId);





}


