package com.example.admin.Controller;

import com.example.library.service.imple.AdminServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class loginController {

    private AdminServiceImpl adminServiceImpl ;

    public loginController(AdminServiceImpl adminServiceImpl) {
        this.adminServiceImpl = adminServiceImpl;
    }

    @GetMapping("/login")
    public String getLoginForm(Model model)
    {
        return "login";
    }
    @GetMapping("/index")
    public String HomePage(Model model)
    {
        return "Index";
    }



}
