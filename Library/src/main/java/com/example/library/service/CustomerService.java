package com.example.library.service;

import com.example.library.dto.CustomerDto;
import com.example.library.dto.PasswordDto;
import com.example.library.model.Address;
import com.example.library.model.Customer;
import com.example.library.repository.CustomerRepository;
import com.example.library.service.imple.CustomerNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public interface CustomerService {
//    private CustomerRepository customerRepo;


    Customer findByEmail(String email);
    CustomerDto findByIdProfile(long id);


    Customer getByResetPassword();

    Customer save(CustomerDto customerDto);

    Customer update(CustomerDto customerDto,long id);


    List<Customer> findAll();

    Customer saveInfo(Customer customer, Address address);

    void disable_enable(long id);

    Customer findById(long id);

    void changePassword(long id,PasswordDto passwordDto);


//    void changePassword(PasswordDto passwordDto, long id);

    String otpGenerate(String username);

    boolean verifyOTP(long otp, String username);


//   public void updateResetPasswordTocken(String tocken,String email)throws CustomerNotFoundException
//   {
//       Customer customer= customerRepo.findByUsername(email);
//
//
//       if(customer==null)
//       {
//           throw new CustomerNotFoundException("c is found"+email);
//       }
//
//       customer.setResetPasswordTocken(tocken);
//       customerRepo.save(customer);
//   }


//    void saveUser(CustomerDto customerDto);
}
