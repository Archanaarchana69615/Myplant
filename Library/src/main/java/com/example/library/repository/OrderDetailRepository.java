package com.example.library.repository;

import com.example.library.model.Order;
import com.example.library.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails,Long> {

    @Query("select o from Order o where o.customer.id=?1")
    List<Order>findAllByCustomerId(Long id);



}
