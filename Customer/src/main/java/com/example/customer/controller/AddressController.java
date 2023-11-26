package com.example.customer.controller;

import com.example.library.dto.AddressDto;
import com.example.library.dto.CustomerDto;
import com.example.library.model.Address;
import com.example.library.model.Category;
import com.example.library.model.Customer;
import com.example.library.model.Order;
import com.example.library.repository.CustomerRepository;
import com.example.library.repository.OrderRepository;
import com.example.library.service.AddressService;
import com.example.library.service.AdminService;
import com.example.library.service.Categoryservice;
import com.example.library.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.aspectj.apache.bcel.util.Repository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller

public class AddressController {

    private final CustomerService customerService;

    private final CustomerRepository customerRepository;

   private final OrderRepository orderRepository;
    private final Categoryservice categoryservice;

    private final AddressService addressService;

    public AddressController(CustomerService customerService, CustomerRepository customerRepository, OrderRepository orderRepository, Categoryservice categoryservice, AddressService addressService) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.categoryservice = categoryservice;
        this.addressService = addressService;
    }

    @GetMapping("/account")
    public String address(Model model, Principal principal, HttpServletRequest httpServletRequest)
    {
        if(principal==null)
        {
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer= customerService.findByEmail(username);

        List<Order>orders = orderRepository.findByCustomer(customer);
        Collections.sort(orders, Collections.reverseOrder(Comparator.comparing(Order::getId)));
        model.addAttribute("orders",orders);

        model.addAttribute("customer",customer);
        List<Address> addressList=addressService.findTrueById(customer.getId());
        model.addAttribute("addresses",addressList);
//        model.addAttribute("addresses",customer.getAddresses());

        HttpSession httpSession1 = httpServletRequest.getSession();
        String name=null;
        if(httpSession1 !=null)
        {
            name= httpServletRequest.getRemoteUser();
        }
        model.addAttribute("name",name);

        return "page-account";
    }

    @GetMapping("/add-address")
    public String addAddress(Model model,Principal principal)
    {
        if(principal==null)
        {
            return "redirect:/login";
        }
        AddressDto addressDto =new AddressDto();
        model.addAttribute("addressDto",addressDto);

        return "add-address";
    }
    @PostMapping("/save-address")
    public String saveAddress(Model model, Principal principal, @ModelAttribute("AddressDto")AddressDto addressDto, RedirectAttributes redirectAttributes)
    {
        if(principal==null)
        {
            return "redirect:/login";
        }
      String username=principal.getName();
        Address newaddress = new Address();
         newaddress =addressService.save(addressDto,username);
         model.addAttribute("newaddress",newaddress);

         redirectAttributes.addFlashAttribute("message","address added");
         return "redirect:/account";
    }
  @GetMapping("/edit-address/{id}")
    public String editAddress(Model model, @PathVariable("id")Long id,Principal principal,HttpServletRequest request)
  {
      if(principal==null)
      {
          return "redirect:/login";
      }
      HttpSession session= request.getSession();
      String previousPageUrl = request.getHeader("referer");
      session.setAttribute("previousPageUrl",previousPageUrl);
      AddressDto addressDto= addressService.findById(id);
      model.addAttribute("addressDto",addressDto);
      return "edit-address";
  }
  @GetMapping("/delete-address/{id}")
  public String deleteAddress(@PathVariable("id")Long id,Principal principal,HttpServletRequest request,Model model)
  {
      if(principal==null)
      {
          return "redirect:/login";
      }
      HttpSession session=request.getSession();
//      session.setAttribute("previo");
      AddressDto addressDto=addressService.findById(id);
      model.addAttribute("addressDto",addressDto);

      addressService.deleteById(id);
      return "redirect:/account";
  }

  @PostMapping("/update-address/{id}")
    public String updateAddress(@PathVariable("id") Long id,HttpServletRequest request,Principal principal,@ModelAttribute("addressDto")AddressDto addressDto,RedirectAttributes redirectAttributes)
  {
      if (principal == null)
      {
          return "redirect:/login";
      }
      HttpSession session = request.getSession();
      String previousPageUrl = (String) session.getAttribute("previousPageUrl");

      String referer = request.getHeader("referer");

      System.out.println(referer);
      Address newaddress= addressService.update(addressDto,id);
      redirectAttributes.addFlashAttribute("message","address updated");
      if(previousPageUrl != null)
      {
          return "redirect:"+ previousPageUrl;
      }
      else {
          return "redirect:/accounts";
      }
  }
  @PostMapping("/add-address-checkout")
    public String AddAddress(@ModelAttribute("AddressDto") AddressDto addressDto,Model model,Principal principal,HttpServletRequest request)
  {

      addressService.save(addressDto,principal.getName());
      model.addAttribute("address",addressDto);

      model.addAttribute("success","added successfully");

      return "redirect:"+ request.getHeader("referer");
  }
  @RequestMapping(value="/update-info",method={RequestMethod.GET,RequestMethod.PUT})
  public String updateCustomer(@ModelAttribute Customer customer,Model model,Principal principal,RedirectAttributes redirectAttributes)
  {
          if(principal==null)
          {
               return "redirect:/login";
          }
          Customer customerSaved = customerService.saveInfo(customer,new Address());
          redirectAttributes.addFlashAttribute("customer",customerSaved);

                  return "redirect:/account";

  }
  @GetMapping("/add-profile")
    public String addProfile(Model model,Principal principal)
  {
      if(principal==null)
      {
          return "redirect:/login";
      }
      String username =principal.getName();
      Customer customer = customerService.findByEmail(username);
      CustomerDto customerDto =new CustomerDto();
      model.addAttribute("customerDto",customerDto);


      return "/profile";
  }


    @GetMapping("/edit-profile/{id}")
    public String editProfile(Model model, @PathVariable("id")Long id,Principal principal,HttpServletRequest request)
    {
        if(principal==null)
        {
            return "redirect:/login";
        }
        HttpSession session= request.getSession();
        String previousPageUrl = request.getHeader("referer");
        session.setAttribute("previousPageUrl",previousPageUrl);
        CustomerDto customerDto=customerService.findByIdProfile(id);
        model.addAttribute("customerDto",customerDto);
        return "edit-profile";
    }

    @PostMapping("/update-profile/{id}")
    public String updateProfile(@PathVariable("id")Long id,HttpServletRequest request,Principal principal,@ModelAttribute CustomerDto customerDto,RedirectAttributes redirectAttributes)
    {
        if(principal==null)
        {
            return "redirect:/login";
        }
        HttpSession session= request.getSession();
        String previousPageUrl = (String) session.getAttribute("previousPageUrl");

        String referer = request.getHeader("referer");

        Customer newcustomer=customerService.update(customerDto,id);
        redirectAttributes.addFlashAttribute("message","profile updated");
        if(previousPageUrl != null)
        {
            return "redirect:"+ previousPageUrl;
        }
        else {
            return "redirect:/accounts";
        }
    }


}
