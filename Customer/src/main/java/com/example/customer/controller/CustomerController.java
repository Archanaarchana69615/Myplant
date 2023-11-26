package com.example.customer.controller;

import com.example.customer.config.CustomerDetails;
import com.example.library.dto.CustomerDto;
import com.example.library.dto.PasswordDto;
import com.example.library.model.Category;
import com.example.library.model.Customer;
import com.example.library.repository.CustomerRepository;
import com.example.library.service.Categoryservice;
import com.example.library.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class CustomerController {

    private CustomerService customerService;

    private CustomerRepository repository;

    private final BCryptPasswordEncoder passwordEncoder;


    public CustomerController(CustomerService customerService, CustomerRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.customerService = customerService;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getDashboard(@RequestParam(required = false) String tab, Model model, Principal principal, HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            Customer customer = customerService.findByEmail(principal.getName());
            session.setAttribute("userlogedin", true);
            session.setAttribute("username", customer.getFirstname() + " " + customer.getLastname());
            if (tab != null && !tab.isEmpty()) {
                model.addAttribute("open", tab);
                System.out.println(tab);

            } else {
                model.addAttribute("open", "");
            }
            model.addAttribute("customer", customer);
            model.addAttribute("title", "dashboard");
            return "Index";
        }

    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        CustomerDto userDto = new CustomerDto();
        model.addAttribute("userDto", userDto);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("customerDto") CustomerDto customerDto, Model model, BindingResult result) {
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

    @GetMapping("/forgot-password")
    public String forgotPasswordOTP(Model model, CustomerDto customerDto) {

        model.addAttribute("title", "Forgot Password- OTP");
        model.addAttribute("username", customerDto);

        return "enterUsername";
    }

    @PostMapping("/forgot-password")
    public String forgotPasswordOTPSend(@ModelAttribute("username") CustomerDto customerDto, Model model) {
        String otp = customerService.otpGenerate(customerDto.getUsername());
        model.addAttribute("data", customerDto);
        model.addAttribute("otpGenerationTime", System.currentTimeMillis());

        return "otpScreen";
    }


    @PostMapping("/forgot-password/otpVerification")
    public String otpSentEmailPost(@ModelAttribute("data") CustomerDto customerDto, Model model, RedirectAttributes attributes) {
        boolean isOTPValid = customerService.verifyOTP(customerDto.getOtp(), customerDto.getUsername());
        if (isOTPValid) {
            model.addAttribute("customerDto", customerDto);
            return "passwordResetPage";
        } else {
            model.addAttribute("error", "Invalid OTP. Please try again.");
            return "otpScreen";
        }
    }


    @PostMapping("/passwordResetPage")
    public String passwordResetPage(@ModelAttribute("customerDto") CustomerDto customerDto, Model model, RedirectAttributes redirectAttributes) {

        System.out.println(customerDto);
        System.out.println("here");
        System.out.println(customerDto.getPassword());
        System.out.println(customerDto.getRepeatPassword());
        if (customerDto.getPassword().equals(customerDto.getRepeatPassword())) {
            Customer customer = repository.findByUsername(customerDto.getUsername());
            customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
            repository.save(customer);
            redirectAttributes.addFlashAttribute("success", "Password is changed");
        } else {
            redirectAttributes.addFlashAttribute("error", "Passwords are not same");

        }

        return "redirect:/login";
    }


    @GetMapping("/login/otpVerification")
    public String otpSent(Model model, CustomerDto customerDto, HttpServletRequest httpServletRequest) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
        Customer customer = repository.findByUsername(user.getUsername());

        if (customer != null) {
            customer.set_activated(false);
            repository.save(customer);
        }
        model.addAttribute("otpValue", customerDto);

        return "otpScreen";

    }

    @PostMapping("/login/otpVerification")
    public String otpVerification(@ModelAttribute("otpValue") CustomerDto customerDto, HttpServletRequest httpServletRequest) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
        Customer customer = repository.findByUsername(user.getUsername());

        if (httpServletRequest.getRequestURI().equals("/user/index")) {

            if (customer.getOtp() == customerDto.getOtp()) {
                customer.set_activated(true);
                repository.save(customer);
                return "redirect:/index";
            } else {
                return "redirect:/login/otpVerification?error";
            }
        } else {
            return "redirect:/index";
        }
    }

    @GetMapping("/ChangePassword")
    public String showChangepasswordpage(Authentication authentication, Model model) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof org.springframework.security.core.userdetails.User) {
            // Handle the case where the principal is a User object
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) principal;
            // You might want to retrieve additional information from the user, or redirect to an error page.
            // For now, we'll set a default ID.
            PasswordDto passwordDto = new PasswordDto();
            passwordDto.setId(0L); // Set a default ID
            model.addAttribute("passwordDto", passwordDto);
            model.addAttribute("mismatch", " ");
            return "changePassword";
        } else if (principal instanceof com.example.library.model.Customer) {
            // Handle the case where the principal is a Customer object
            com.example.library.model.Customer customer = (com.example.library.model.Customer) principal;
            PasswordDto passwordDto = new PasswordDto();
            passwordDto.setId(customer.getId());
            model.addAttribute("passwordDto", passwordDto);
            model.addAttribute("mismatch", " ");
            return "changePassword";
        } else {
            // Handle the case where the principal is neither User nor Customer
            // You might want to redirect to an error page or handle it appropriately.
            return "errorPage";
        }
    }

    @PostMapping("/ChangePassword")
    public String changepassword(@Valid @ModelAttribute("passwordDto") PasswordDto passwordDto, BindingResult bindingResult, Model model, Principal principal) {
        try {
            if (bindingResult.hasErrors()) {
                // Handle validation errors
                model.addAttribute("passwordDto", passwordDto);
                model.addAttribute("mismatch", " ");
                return "changePassword";
            } else if (!passwordDto.getPassword().equals(passwordDto.getConformpassword())) {
                // Handle password mismatch
                model.addAttribute("mismatch", "Password mismatch, please confirm");
                model.addAttribute("passwordDto", passwordDto);
                return "changePassword";
            } else {
                String username = principal.getName();
                Customer customer = customerService.findByEmail(username);
                long cusId = customer.getId();
                String password = passwordEncoder.encode(passwordDto.getPassword());
                passwordDto.setPassword(password);
                System.out.println(passwordDto);

                customerService.changePassword(cusId, passwordDto);
                model.addAttribute("mismatch", "Password changed");
                model.addAttribute("passwordDto", passwordDto);
                return "redirect:/account";
            }
        } catch (Exception e) {
            // Log the exception or handle it as needed
            model.addAttribute("error", "An error occurred while processing your request");
            return "errorPage";
        }

    }
        @GetMapping("/about")
        public String getAboutUs ()
        {
            return "Index";
        }
        @GetMapping("/contact")
        public String contactUs ()
        {
            return "Index";
        }


    }
