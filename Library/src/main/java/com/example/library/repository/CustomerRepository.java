package com.example.library.repository;

import com.example.library.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
//@Query("select c from Customer c where c.username=?1")
    public Customer findByUsername(String username);

    Customer findById(long id);

//    @Query("select c from Customer c where c.email=?1")
//    public Customer findByEmail(String email);
//
//    public Customer findByResetPasswordTocken(String tocken);



//    Customer save(String username);

}
