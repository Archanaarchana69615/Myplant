package com.example.admin.Controller;

import com.example.library.model.Customer;
import com.example.library.repository.CustomerRepository;
import com.example.library.service.CustomerService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CustomerController {
    private CustomerService customerService;
    private CustomerRepository customerRepository;

    public CustomerController(CustomerService customerService)
    {
        this.customerService=customerService;
    }

    @GetMapping("/customers")
    public String customersList(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        model.addAttribute("size", customers.size());
        return "customers";
    }

    @GetMapping("/disable-customer/{id}")
    public String disable(@PathVariable("id")long id, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("success","Customer Disabled");

        customerService.disable_enable(id);
        return "redirect:/customers";
    }
    @GetMapping("/customer")
    public String customerlist(Model model)
    {
        model.addAttribute("title","list customers");
        List<Customer>customers = customerRepository.findAll();
        model.addAttribute("customers",customers);

        return "redirect:/customers";
    }

}
