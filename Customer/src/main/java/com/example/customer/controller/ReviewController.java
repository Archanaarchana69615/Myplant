package com.example.customer.controller;

import com.example.library.dto.ReviewDto;
import com.example.library.model.Product;
import com.example.library.model.Review;
import com.example.library.service.Productservice;
import com.example.library.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
@Controller
public class ReviewController {
    private ReviewService reviewService;
    private Productservice productservice;

    public ReviewController(ReviewService reviewService, Productservice productservice) {
        this.reviewService = reviewService;
        this.productservice = productservice;
    }

    @PostMapping("/addReview")
    public String addReview(@ModelAttribute("reviewDto") ReviewDto reviewDto,
                            @RequestParam("productId") Long productId,
                            Principal principal){
        if(principal==null){
            return "redirect:/loginPage";
        }
        System.out.println("add review");

        String username=principal.getName();

        reviewService.save(reviewDto,username,productId);


        return "redirect:/showReviewPage?productId="+productId;
    }
    @GetMapping("/showReviewPage")
    public String showReview(@RequestParam("productId")Long id, Model model){
        ReviewDto reviewDto=new ReviewDto();
        Product product=productservice.getById(id);
        List<Review> reviews=reviewService.readReviewByProduct(id);
        model.addAttribute("review",reviews);
        model.addAttribute("product",product);
        model.addAttribute("reviewDto",reviewDto);
        return "review-rating";
    }

}
