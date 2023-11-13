package com.example.library.service.imple;

import com.example.library.dto.CustomerDto;
import com.example.library.model.Address;
import com.example.library.model.Customer;
import com.example.library.model.EmailDetails;
import com.example.library.repository.CustomerRepository;
import com.example.library.repository.RoleRepository;
import com.example.library.service.CustomerService;
import com.example.library.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
//@AllArgsConstructor
//@NoArgsConstructor
@Service
public class CustomerserviceImple implements CustomerService {

    private CustomerRepository customerRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;


   private EmailService emailService;

    public CustomerserviceImple(CustomerRepository customerRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    private static final long OTP_VALID_DURATION =1*60*1000;

    public Date getOtpRequestedTime(){
        return otpRequestedTime;
    }
public void setOtpRequestedTime(Date otpRequestedTime)
{
    this.otpRequestedTime = otpRequestedTime;
}

private Date otpRequestedTime;

    long otpRequsetedTimeMillis=0;


    @Override
    public Customer findByEmail(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public Customer getByResetPassword() {
        return null;
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

//    @Override
//    public String otpGenerate(String username) {
//        return null;
//    }

//    @Override
//    public String otpGenerate(String username) {
//        Customer customer=customerRepository.findByUsername(username);
//        int otp=(int)(Math.random()*9000)+1000;
//        customer.setOtp(otp);
//        customerRepository.save(customer);
//        setOtpRequestedTime(new Date());
//        otpRequsetedTimeMillis= otpRequestedTime.getTime();
//        return emailService.sendSimpleMail(new EmailDetails(username,"your otp for verificatonis"+otp,"verify with otp"));
//    }

//    @Override
//    public void updateResetPasswordTocken(String tocken, String email) throws CustomerNotFoundException {
//
//    }


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



