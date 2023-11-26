package com.example.library.repository;

import com.example.library.model.Order;
import com.example.library.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails,Long> {

    @Query("select o from Order o where o.customer.id=?1")
    List<Order>findAllByCustomerId(Long id);

     @Query("SELECT p.id, p.name, p.category.name, p.costPrice, SUM(od.quantity), SUM(od.totalPrice) " +
            "FROM OrderDetails od " +
            "INNER JOIN od.product p " +
            "GROUP BY p.id, p.name, p.category, p.costPrice")
    List<Object[]> findProductsSoldAndRevenue();
//@Query("SELECT p.id, p.name, p.category.name, p.costPrice, SUM(od.quantity), SUM(od.totalPrice) " +
//        "FROM OrderDetails od " +
//        "INNER JOIN od.product p " +
//        "INNER JOIN p.category c " + // Join the category association
//        "GROUP BY p.id, p.name, c.name, p.costPrice")
//List<Object[]> findProductsSoldAndRevenue();



    @Query("SELECT p.id, p.name, p.category.name, p.costPrice, SUM(od.quantity), SUM(od.totalPrice) " +
            "FROM OrderDetails od " +
            "INNER JOIN od.product p " +
            "INNER JOIN od.order o " +
            "WHERE YEAR(o.orderDate) = :year AND MONTH(o.orderDate) = :month " +
            "GROUP BY p.id, p.name, p.category, p.costPrice" +
            " ORDER BY p.id")

    List<Object[]>findProductSoldAndRevenueByMonthAndYear(@Param("month")int month,@Param("year")int year);


}
