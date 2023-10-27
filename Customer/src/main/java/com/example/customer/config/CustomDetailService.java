package com.example.customer.config;


import com.example.library.model.Customer;
import com.example.library.model.Role;
import com.example.library.repository.CustomerRepository;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomDetailService implements UserDetailsService
{


    private final CustomerRepository customerRepository;


    public CustomDetailService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username"+username);
        Customer customer = customerRepository.findByUsername(username);

        if (customer.is_activated()==true) {
            throw new LockedException("This user is blocked");
        }

        if(customer !=null) {
                List<GrantedAuthority> authorities = new ArrayList<>();
                for (Role role : customer.getRoles()) {
                    authorities.add(new SimpleGrantedAuthority(role.getName()));
                }

                return new CustomerDetails(
                        customer.getUsername(),
                        customer.getPassword(),
                        authorities,
                        customer.getFirstname(),
                        customer.getLastname(),
                        customer.getMobilenumber(),
                        customer.is_activated());

        }
        else
            throw new UsernameNotFoundException("Invalid username or password.");
    }
}
