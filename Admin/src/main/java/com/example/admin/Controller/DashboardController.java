package com.example.admin.Controller;

import com.example.library.service.Productservice;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import com.example.library.service.Categoryservice;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {


    public DashboardController(Productservice productservice, Categoryservice categoryservice) {
        this.productservice = productservice;
        this.categoryservice = categoryservice;
    }
    private Productservice productservice;
    private Categoryservice categoryservice;


}
