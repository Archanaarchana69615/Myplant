package com.example.customer.config;

import com.example.library.model.Customer;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public class CustomerDetails extends org.springframework.security.core.userdetails.User{

    private String firstname;

    private String lastname;

    private String mobilenumber;

    private boolean is_activated;


    public CustomerDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,String firstname,String lastname, String mobilenumber,boolean is_activated ) {
        super(username, password, authorities);
        this.firstname =firstname;
        this.lastname=lastname;
        this.mobilenumber=mobilenumber;
        this.is_activated=is_activated;
    }
}
