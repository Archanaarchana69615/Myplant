package com.example.library.dto;


import com.example.library.model.Category;
import com.example.library.model.Image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

   private Long id;

   private String name;

   private String description;

   private double costPrice;

   private double salesPrice;

   private int currentQuantity;

   private List<Image> image;

   private Category category;

   private boolean activated;
}
