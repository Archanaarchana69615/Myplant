package com.example.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {


    private long id;

    private ShoppingCartDto cart;


    private ProductDto product;

    private int quantity;


}
