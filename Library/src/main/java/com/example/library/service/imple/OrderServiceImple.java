package com.example.library.service.imple;

import com.example.library.model.Order;
import com.example.library.model.ShoppingCart;
import com.example.library.repository.CustomerRepository;
import com.example.library.repository.OrderRepository;
import com.example.library.repository.ShoppingCartRepository;
import com.example.library.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImple implements OrderService {

    private OrderRepository repository;

    private CustomerRepository customerRepository;

    private ShoppingCartRepository shoppingCartRepository;
    @Override
    public Order save(ShoppingCart cart, long address_id, String payementMethod, Double oldTotalPrice) {
        return null;
    }
}
