package com.example.customer.controller;

import com.example.library.dto.ProductDto;
import com.example.library.model.Category;
import com.example.library.model.Customer;
import com.example.library.model.Product;
import com.example.library.model.Review;
import com.example.library.service.Categoryservice;
import com.example.library.service.CustomerService;
import com.example.library.service.Productservice;
import com.example.library.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class ProductController {

    private Productservice productservice;

    private Categoryservice categoryservice;

    private CustomerService customerService;

    private ReviewService reviewService;

    public ProductController(Productservice productservice, Categoryservice categoryservice, CustomerService customerService, ReviewService reviewService) {
        this.productservice = productservice;
        this.categoryservice = categoryservice;
        this.customerService = customerService;
        this.reviewService = reviewService;
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String homepage(Model model, Principal principal, HttpSession session) {
        if (principal != null) {
            Customer customer = customerService.findByEmail(principal.getName());
            session.setAttribute("userLogedIn", true);
            session.setAttribute("username", customer.getFirstname() + " " + customer.getLastname());
        }
        List<Category> categories = categoryservice.findAllActivatedTrue();
        List<ProductDto> products = productservice.findAllProducts();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        return "Index";
    }

    @GetMapping("/product-full-list")
    public String productPage(Model model) {
        model.addAttribute("page", "Products");
        model.addAttribute("title", "Menu");
        List<Category> categories = categoryservice.findAllActivatedTrue();
        List<ProductDto> products = productservice.findAllProducts();
        model.addAttribute("totalProducts", products.size());
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);


        return "product-full-list";
    }

    @GetMapping("/product-list/{id}")
    public String getProductFull(@PathVariable("id")long id, Model model){
        List<Category> categories = categoryservice.findAllActivatedTrue();
        ProductDto productDto=productservice.findById(id);
        Double reviewDto=reviewService.findRatingByProduct(id);
        System.out.println("review rate="+reviewDto);
        List<Review> reviews=reviewService.readReviewByProduct(id);

        model.addAttribute("categories",categories);
        model.addAttribute("product",productDto);
        model.addAttribute("reviews",reviews);
        return "product-list";
    }





    }


