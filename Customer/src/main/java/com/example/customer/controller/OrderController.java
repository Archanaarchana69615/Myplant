package com.example.customer.controller;

import com.example.library.service.AddressService;
import com.example.library.service.CustomerService;
import com.example.library.service.OrderService;
import com.example.library.service.ShoppingCartService;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {

    private CustomerService customerService;
    private ShoppingCartService shoppingCartService;

    private OrderService orderService;

    private AddressService addressService;


    public OrderController(CustomerService customerService, ShoppingCartService shoppingCartService,
                           OrderService orderService, AddressService addressService) {
        this.customerService = customerService;
        this.shoppingCartService = shoppingCartService;
        this.orderService = orderService;
        this.addressService = addressService;
    }

}
