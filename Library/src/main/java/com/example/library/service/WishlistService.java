package com.example.library.service;

import com.example.library.model.Customer;
import com.example.library.model.Wishlist;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WishlistService {

    List<Wishlist>findAllByCustomer(Customer customer);


    void createWishlist(String wishlistName,Customer customer);


   Wishlist findById(long id);

   boolean findByProductId(long id,Customer customer);

   Wishlist save(long productId,Customer customer);

   Wishlist delete(long id);

}
