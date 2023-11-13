package com.example.library.service;

import com.example.library.dto.AddressDto;
import com.example.library.model.Address;
import com.example.library.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {
    Address save(AddressDto addressDto,String username );


    AddressDto findById(long id);

    Address update(AddressDto addressDto,long id);

//    Address findByCustomerId(Customer customer);

    Address findByIdOrder(long id);

    Address findDefaultAddress(long id);

    void deleteById(long id);

//    Address update(AddressDto);

    List<Address> findTrueById(long id);
}
