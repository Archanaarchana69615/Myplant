package com.example.library.service;

import com.example.library.dto.CategoryDto;

import com.example.library.model.Category;

import java.util.List;


public interface Categoryservice {

    List<Category>findAll();

    Category save(CategoryDto category);
    Category getById(Long id);
    Category update(Category category);

    Category findById(Long id);
    Category deleteById(Long id);
    void enableById(Long id);

    void disableById(Long id);

    List<Category> findAllActivatedTrue();

    boolean existsById(Long id);

    void saveOrUpdate(Category category);


//    List<com.example.library.model.Category> findAll();
}
