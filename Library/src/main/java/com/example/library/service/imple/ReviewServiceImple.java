package com.example.library.service.imple;

import com.example.library.dto.ReviewDto;
import com.example.library.model.Product;
import com.example.library.model.Review;
import com.example.library.repository.CouponRepository;
import com.example.library.repository.ReviewRepository;
import com.example.library.service.CustomerService;
import com.example.library.service.Productservice;
import com.example.library.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class ReviewServiceImple implements ReviewService {
private Productservice productservice;

private ReviewRepository reviewRepository;

private CustomerService customerService;

    public ReviewServiceImple(Productservice productservice, ReviewRepository reviewRepository, CustomerService customerService) {
        this.productservice = productservice;
        this.reviewRepository = reviewRepository;
        this.customerService = customerService;
    }


    @Override
    public List<Review> readReviewByProduct(Long productId) {
        return reviewRepository.readReviewByProduct(productId);

    }

    @Override
    public Double findRatingByProduct(Long productId) {
        return null;
    }

    @Override
    public Review save(ReviewDto reviewDto, String email, Long productId) {
        Product product=productservice.getById(productId);
        Review review=new Review();
        review.setReviewDate(new Date());
        review.setComment(reviewDto.getComment());
        review.setRating(reviewDto.getRating());
        review.setCustomer(customerService.findByEmail(email));
        review.setProduct(product);
        return reviewRepository.save(review);
    }
}
