package com.example.library.service;


import com.example.library.model.Customer;
import com.example.library.model.Order;
import com.example.library.model.ShoppingCart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    Order save(ShoppingCart cart,long address_id,String payementMethod,Double OldTotalPrice);

   Order findOrderById(long id);

   void cancelOrder(long id);

    void updateOrderStatus(String status ,long order_id);

    List<Order> findAllOrders();

    void acceptOrder(Long id);

    String getOrderStatus(Long orderId);

    void updatePayment(Order order,boolean status);







}
