package com.example.library.service.imple;

import com.example.library.dto.CustomerDto;
import com.example.library.model.Address;
import com.example.library.model.Customer;
import com.example.library.repository.CustomerRepository;
import com.example.library.repository.RoleRepository;
import com.example.library.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
//@AllArgsConstructor
//@NoArgsConstructor
@Service
public class CustomerserviceImple implements CustomerService {

    private CustomerRepository customerRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;




    public CustomerserviceImple(CustomerRepository customerRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Customer findByEmail(String username) {
        return customerRepository.findByUsername(username);
    }
 @Override
    public Customer save (CustomerDto customerDto)
 {
     Customer customer= new Customer();
     customer.setId(customerDto.getId());
     customer.setFirstname(customerDto.getFirstname());
     customer.setLastname(customerDto.getLastname());
     customer.setMobilenumber(customerDto.getMobilenumber());
     customer.setUsername(customerDto.getUsername());
     customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
     customer.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));

     return customerRepository.save(customer);
 }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer saveInfo(Customer customer, Address address) {
        return null;
    }

    public Customer findById(long id) {
        return customerRepository.findById(id);
    }

    @Override
    public void disable_enable(long id) {
        Customer customer = customerRepository.getReferenceById(id);
        if (customer.is_activated()==false){
            customer.set_activated(true);
        }else {
            customer.set_activated(false);
        }
        customerRepository.save(customer);

    }

//    @Override
//    public void saveUser(CustomerDto customerDto) {
//        Customer customer = new Customer();
//        customer.setFirstname(customerDto.getFirstname() + " " + customerDto.getFirstname());
//        customer.setUsername(customerDto.getUsername());
//        customer.setMobilenumber(customerDto.getMobilenumber());
//        customer.setLastname(customerDto.getLastname());
////        customer.set_activated(customerDto.g);
//        customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
////        customer.setRole("USER");
//
//
//        customerRepository.save(customer);
//    }
    }



