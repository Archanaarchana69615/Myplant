package com.example.admin.configuration;

import com.example.library.model.Admin;
import com.example.library.model.Role;
import com.example.library.repository.AdminRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class AdminDetailService implements UserDetailsService {

    private AdminRepository adminRepository;


    public AdminDetailService(AdminRepository adminRepository)
    {
        this.adminRepository=adminRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username);

        if (admin!=null)
        {
            List<GrantedAuthority> authorities = new ArrayList();
            for (Role role : admin.getRoles())
            {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }
            return new AdminDetails(
                    admin.getFirstName(),
                    admin.getPassword(),
                    authorities,
                    admin.getFirstName(),
                    admin.getLastName(),
                    admin.getMobileNumber());
        }
        else {
            throw new UsernameNotFoundException("Invalid username or password");
        }

    }
}
