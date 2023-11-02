package com.example.library.service;

import com.example.library.dto.ProductDto;
import com.example.library.model.ShoppingCart;
import org.springframework.stereotype.Service;

@Service

public interface ShoppingCartService {

    ShoppingCart addItemToCart(ProductDto productDto,int quantity, String username);

    ShoppingCart updateCart(ProductDto productDto,int quantity,String username,Long cart_Item_Id);

    ShoppingCart removeItemFromCart(ProductDto productDto, String username);

//    ShoppingCart find(ProductDto productDto,String username);
}
