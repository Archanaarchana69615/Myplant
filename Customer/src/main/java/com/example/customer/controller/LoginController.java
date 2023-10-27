package com.example.customer.controller;

import com.example.library.dto.CustomerDto;
import com.example.library.model.Customer;
import com.example.library.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private CustomerService customerService;


    public LoginController(CustomerService customerService) {
        this.customerService = customerService;

    }

 @GetMapping("/login")
public String getLoginForm(Model model, HttpSession session)
{
    model.addAttribute("title","Login page");
    Object attribute=session.getAttribute("userlogedin");
            if(attribute!=null)
            {
              return "redirect:/login";
            }

    return "login";
}

}


