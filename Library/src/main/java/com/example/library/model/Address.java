package com.example.library.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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


    @NotBlank
    private String addressline1;
    @NotBlank
    private String addressline2;
    @NotBlank
    private String pincode;
    @NotBlank
    private String state;
    @NotBlank
    private String city;
    @NotBlank
    private String district;
    @NotBlank
    private String country;

    private boolean is_default;

    private boolean is_activated;

    private boolean is_deleted;

    @ToString.Exclude
    @OneToMany(mappedBy = "shippingAddress")
    private List<Order> order;

     @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH})
     @JoinColumn(name = "customer_id",referencedColumnName = "customer_id")
     private Customer customer;


}
