package com.example.library.service;

import com.example.library.dto.ProductDto;
import com.example.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface Productservice  {

//    List<Product> findAll();

    List<ProductDto> findAll();

    ProductDto findById(Long id);

    Product findBYId(long id);

    List<ProductDto> findAllByOrder();

    Product update(List<MultipartFile> imageProduct,ProductDto productDto);

    Product save(List<MultipartFile>imageProduct,ProductDto product);

    Product disable(long id);

    Product enable(long id);

    Product deleted(long id);


    List<ProductDto> findAllProducts();

    Page<Product> getAllProducts(int pageNo);

    Product getById(Long id);

}
