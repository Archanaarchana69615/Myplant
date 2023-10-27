package com.example.library.repository;

import com.example.library.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

        Address findById(long id);
}
