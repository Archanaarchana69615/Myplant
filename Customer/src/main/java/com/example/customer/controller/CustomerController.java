package com.example.customer.controller;

import com.example.library.dto.CustomerDto;
import com.example.library.model.Category;
import com.example.library.model.Customer;
import com.example.library.service.Categoryservice;
import com.example.library.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@AllArgsConstructor
@Controller
public class CustomerController {

    private CustomerService customerService;




    @RequestMapping(value = "/index" , method = RequestMethod.GET)
    public String getDashboard(@RequestParam(required=false)String tab,Model model, Principal principal,HttpSession session)
    {
        if(principal==null)
        {
            return "redirect:/login";
        }
        else {
            Customer customer=customerService.findByEmail(principal.getName());
                    session.setAttribute("userlogedin",true);
                    session.setAttribute("username",customer.getFirstname()+ " "+customer.getLastname());
                    if(tab!=null && !tab.isEmpty())
                    {
                       model.addAttribute("open",tab);
                       System.out.println(tab);

                    }
                    else {
                        model.addAttribute("open","");
                    }
                    model.addAttribute("customer",customer);
                    model.addAttribute("title","dashboard");
                    return "Index";
        }

    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        CustomerDto userDto = new CustomerDto();
        model.addAttribute("userDto", userDto);
        return "register";
    }
    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute ("customerDto")CustomerDto customerDto, Model model, BindingResult result ){
        Customer existing = customerService.findByEmail(customerDto.getUsername());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("userDto", customerDto);
            return "register";
        }
        customerService.save(customerDto);
        return "redirect:/register?success";
    }

//    @GetMapping("/forgot-password")
//    public String forgotPassword(Model model,CustomerDto customerDto)
//    {
//        model.addAttribute("title","forgot otp");
//        model.addAttribute("username",customerDto);
//
//
//        return "enterUsername";
//    }
//
//    @PostMapping("/forgot-password")
//    public String forgotPassword(@ModelAttribute("username")CustomerDto customerDto,Model model)
//    {
//      String otp=customerService.otpGenerate(customerDto.getUsername());
//      model.addAttribute("data",customerDto);
//      model.addAttribute("otpGenerateTime",System.currentTimeMillis());
//      return "otpScreen";
//    }




    @GetMapping("/about")
    public String getAboutUs()
    {
        return "Index";
    }
    @GetMapping("/contact")
    public String contactUs()
    {
        return "Index";
    }



}
