package com.example.library.repository;


import com.example.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
  Product findById(long id);

  @Query("select p from Product p JOIN Category c ON p.category.id=c.id")
  Page<Product> findAllProductPagable(Pageable pageable);




}
