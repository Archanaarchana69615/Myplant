package com.example.library.service;


import com.example.library.model.Order;
import com.example.library.model.ShoppingCart;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    Order save(ShoppingCart cart,long address_id,String payementMethod,Double oldTotalPrice);
}
