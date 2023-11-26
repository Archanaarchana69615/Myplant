package com.example.library.repository;


import com.example.library.model.Customer;
import com.example.library.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findById(long Id);


    List<Order> findByCustomer(Customer customer);

    List<Order>findByOrderDateBetween(Date startDate, Date endDate);

    @Query(value="SELECT DATE_TRUNC('day', o.order_date) AS date, SUM(SUM(o.total_price)) OVER (PARTITION BY DATE_TRUNC('day', o.order_date)) AS earnings FROM orders o WHERE EXTRACT(YEAR FROM o.order_date) = :year AND EXTRACT(MONTH FROM o.order_date) =:month GROUP BY DATE_TRUNC('day', o.order_date)",nativeQuery = true)
    List<Object[]> dailyEarnings(@Param("year") int year, @Param("month") int month);


    @Query("SELECT o.payementMethod, SUM(o.totalPrice) FROM Order o WHERE o.payementMethod IN ('COD', 'RazorPay', 'Wallet') GROUP BY o.payementMethod")
    List<Object[]> findTotalPricesByPaymentMethod();

    int countByIsAcceptIsFalse();

    List<Order>findById(Customer customer);



}
