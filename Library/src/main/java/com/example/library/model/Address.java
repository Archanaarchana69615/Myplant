package com.example.library.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class Address  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")

    private Long id;

    private String addressline1;
    private String addressline2;
    private String pincode;
    private String state;
    private String city;
    private String district;
    private String country;
    private boolean is_default;

     @OneToMany(mappedBy = "shipping address")
    private List<Order> order;

     @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH})
     @JoinColumn(name = "customer_id",referencedColumnName = "customer_id")
     private Customer customer;
}
