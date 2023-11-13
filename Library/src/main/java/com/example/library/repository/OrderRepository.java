package com.example.library.repository;


import com.example.library.model.Customer;
import com.example.library.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findById(long Id);


    List<Order> findByCustomer(Customer customer);

//    List<Order>findByOrderDateBetween(Date startDate, Date endDate);


    int countByIsAcceptIsFalse();

    List<Order>findById(Customer customer);



}
