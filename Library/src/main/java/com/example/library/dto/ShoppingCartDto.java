package com.example.library.dto;

import com.example.library.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDto {

    private long id;

    private Customer customer;

    private double totalprice;

    private int totalitems;


    private Set<CartDto> cartitems;


}
