package com.example.library.service.imple;

import com.example.library.exception.DuplicateWishlistNameException;
import com.example.library.model.Customer;
import com.example.library.model.Product;
import com.example.library.model.Wishlist;
import com.example.library.repository.WishlistRepository;
import com.example.library.service.Productservice;
import com.example.library.service.WishlistService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WishlistServiceImple implements WishlistService {

    private WishlistRepository wishlistRepository;
    private Productservice productservice;

    public WishlistServiceImple(WishlistRepository wishlistRepository, Productservice productservice) {
        this.wishlistRepository = wishlistRepository;
        this.productservice = productservice;
    }

    @Override
    public List<Wishlist> findAllByCustomer(Customer customer) {
        List<Wishlist>wishlists=wishlistRepository.findAllByCustomerId(customer.getId());
        return wishlists ;
    }

    @Override
    public void createWishlist(String wishlistName, Customer customer) {
        Wishlist existingwishlist=wishlistRepository.findByCustomerAndWishlistName(customer,wishlistName);

        if(existingwishlist!=null)
        {
          throw new DuplicateWishlistNameException("wishlist name already exists");
        }
        Wishlist wishlist=new Wishlist();
        wishlist.setWishlistName(wishlistName);
        wishlist.setCustomer(customer);
        wishlistRepository.save(wishlist);
    }

    @Override
    public Wishlist findById(long id) {
        return wishlistRepository.findById(id);
    }

    @Override
    public boolean findByProductId(long productId, Customer customer) {
        boolean exists=wishlistRepository.findByProductIdAndCustomerId(productId, customer.getId());
        return exists;
    }

    @Override
    public Wishlist save(long productId, Customer customer) {
        Product product=productservice.findBYId(productId);
        Wishlist wishlist=new Wishlist();
        wishlist.setProduct(product);
        wishlist.setCustomer(customer);

        wishlistRepository.save(wishlist);
        return wishlist;
    }

    @Override
    public Wishlist delete(long id) {
        Wishlist wishlist=wishlistRepository.findById(id);
        wishlistRepository.delete(wishlist);
        return wishlist;
    }
}
