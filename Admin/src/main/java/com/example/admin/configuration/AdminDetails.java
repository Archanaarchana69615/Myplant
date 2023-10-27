package com.example.admin.configuration;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public class AdminDetails extends org.springframework.security.core.userdetails.User implements Serializable {

    private String firstName;

    private String lastname;

    private String mobilenumber;

    public AdminDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,String firstName,String lastname,String mobilenumber)
    {
        super(username,password,authorities);
        this.firstName=firstName;
        this.lastname=lastname;
        this.mobilenumber=mobilenumber;
    }

}
