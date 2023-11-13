package com.example.library.repository;

import com.example.library.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

        Address findById(long id);

        @Query(value = "SELECT * FROM addresses WHERE is_default=true AND customer_id=:id",nativeQuery = true)
        Address findByActivatedTrue(@Param("id")long id);

//        List<Address>  findAddressByCustomerIdAndiAndIs_deletedFalse(@Param("id")long id);
        @Query(value = "SELECT * FROM address WHERE is_deleted=false AND customer_id=:id",nativeQuery = true)
        List<Address> findByTrue(@Param("id")long id);

}
