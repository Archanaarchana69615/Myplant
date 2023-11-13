package com.example.library.repository;

import com.example.library.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Customer,Integer> {

}

