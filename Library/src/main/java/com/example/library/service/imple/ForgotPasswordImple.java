package com.example.library.service.imple;

import com.example.library.repository.CustomerRepository;

public class ForgotPasswordImple {

    private final CustomerRepository customerRepository;

    public ForgotPasswordImple(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
//    public void updateResetPasswordTocken(String tocken,String username)throws CustomernotFoUNDException
//    {
//
//    }
}
