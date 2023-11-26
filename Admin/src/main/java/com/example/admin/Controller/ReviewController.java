package com.example.admin.Controller;

import com.example.library.model.Product;
import com.example.library.model.Review;
import com.example.library.service.Productservice;
import com.example.library.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReviewController {
    private Productservice productservice;
    private ReviewService reviewService;

    public ReviewController(Productservice productservice, ReviewService reviewService) {
        this.productservice = productservice;
        this.reviewService = reviewService;
    }

    @GetMapping("/review/{pageNo}")
    public String showRiew(@PathVariable("pageNo")int pageNo, Model model){
        Page<Product> products=productservice.getAllProducts(pageNo);
        model.addAttribute("product",products);
//        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPage", products.getTotalPages());
        return "review";
    }
    @GetMapping("/showProductReview")
    public String showProductReview(@RequestParam("productId")Long productId, Model model){
        List<Review> reviews=reviewService.readReviewByProduct(productId);
        model.addAttribute("reviews",reviews);
        return "product-review";
    }
    @GetMapping("/rating")
    public String rating(){
        return "rating";
    }
}



