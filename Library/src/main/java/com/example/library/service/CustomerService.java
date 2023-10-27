package com.example.library.service;

import com.example.library.dto.CustomerDto;
import com.example.library.model.Address;
import com.example.library.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    Customer findByEmail(String email);



    Customer save(CustomerDto customerDto);


    List<Customer> findAll();

    Customer saveInfo(Customer customer, Address address);

    void disable_enable(long id);


//    void saveUser(CustomerDto customerDto);
}
