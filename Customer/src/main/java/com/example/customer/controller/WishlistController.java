package com.example.customer.controller;

import com.example.library.exception.DuplicateWishlistNameException;
import com.example.library.model.Customer;
import com.example.library.model.Wishlist;
import com.example.library.service.CustomerService;
import com.example.library.service.WishlistService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
@Controller
public class WishlistController {
    private WishlistService wishlistService;

    private CustomerService customerService;

    public WishlistController(WishlistService wishlistService, CustomerService customerService) {
        this.wishlistService = wishlistService;
        this.customerService = customerService;
    }

    @GetMapping("/wishlist")
    public String wishlist(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Customer customer = customerService.findByEmail(principal.getName());
        List<Wishlist> wishlists = wishlistService.findAllByCustomer(customer);
        if (wishlists.isEmpty()) {
            model.addAttribute("check", "you dont have any wishlist");
        }
            model.addAttribute("wishlists", wishlists);
            return "wishlist";
    }
    @PostMapping("/create-wishlist")
    public String createWishlist(@RequestParam("wishlistName")String wishlistName, Principal principal, RedirectAttributes attributes)
    {
      if(principal==null)
      {
          return "redirect:/login";
      }else {
          Customer customer = customerService.findByEmail(principal.getName());

          try {
              wishlistService.createWishlist(wishlistName, customer);
          } catch (DuplicateWishlistNameException e) {
              attributes.addFlashAttribute("errormessage", e.getMessage());
          }
      }
      return "redirect:/wishlist";
    }

      @GetMapping("/add-wishlist/{id}")
       public String addWishlist(Principal principal, @PathVariable("id") long id, RedirectAttributes attributes, HttpServletRequest request)
      {
          if (principal==null)
          {
              return "redirect:/login";
          }
          Customer customer=customerService.findByEmail(principal.getName());
          Wishlist wishlist=wishlistService.findById(id);
          boolean exists=wishlistService.findByProductId(id,customer);

          if(exists)
          {
              attributes.addFlashAttribute("error","product already exists in wishlist");
              return "redirect:" + request.getHeader("Referer");
          }
          wishlistService.save(id,customer);
          return "redirect:/wishlist";
      }
      @GetMapping("/delete-wishlist/{id}")
      public String deleteWishlist(@PathVariable("id") long wishlistId,RedirectAttributes attributes)
      {
       wishlistService.delete(wishlistId);
       attributes.addFlashAttribute("success","deleted successfully");
       return "redirect:/wishlist";
      }
}
