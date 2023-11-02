package com.example.library.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")

    private Long Id;
    private Date orderdate;
    private Date deliveydate;
    private String orderStatus;
    private Double shippingPrice;
    private double totalPrice;
    private String payementMethod;
    private String payementStatus;
    private boolean isAccept;
    private int quantity;

    @Column(nullable = true)
    private Double discountPrice;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "address_id",referencedColumnName = "address_id")
    private Address shippingAddress;

   @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH})

    @JoinColumn(name = "customer_id",referencedColumnName = "customer_id")
   private Customer customer;

   @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
   private List<OrderDetails>orderDetailList;

   @Column(nullable = true)
   private LocalDateTime confirmedDateTime;

   @Column(nullable = true)
   private LocalDateTime shippedDate;

   @Column(nullable = true)
   private LocalDateTime delivered;




}
