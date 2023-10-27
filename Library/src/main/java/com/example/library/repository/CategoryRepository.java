package com.example.library.repository;

import com.example.library.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("select c from Category c where c.deleted=false")
    List<Category> findAllByDeletedFalse();

    @Query( "select c from Category c where c.activated=true ")
    List<Category> findAllByActivatedTrue();
}