package com.example.library.service;

import com.example.library.dto.AddressDto;
import com.example.library.model.Address;
import org.springframework.stereotype.Service;

@Service
public interface AddressService {
    Address save(AddressDto addressDto,String username );


    AddressDto findById(long id);

    Address update(AddressDto addressDto,long id);

//    Address update(AddressDto);
}
