package com.example.library.service.imple;


import com.example.library.model.Admin;
import com.example.library.repository.AdminRepository;
import com.example.library.repository.RoleRepository;
import com.example.library.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
//@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;
//    private final PasswordEncoder passwordEncoder;


    public AdminServiceImpl(AdminRepository adminRepository, RoleRepository roleRepository) {
        this.adminRepository = adminRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Admin save() {
        Admin admin = new Admin();
        String str ="Archana";
        admin.setFirstName("Archana");
        admin.setLastName("cs");
        admin.setUsername("archanaarchana69615@gmail.com");
        admin.setPassword(passwordEncoder.encode(str));
        admin.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));
        return adminRepository.save(admin);
    }

    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
}
}