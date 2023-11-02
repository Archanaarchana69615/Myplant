//package com.example.customer.controller;
//
//import com.example.library.model.EmailDetails;
//import com.example.library.service.EmailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//
//public class EmailController {
//    @Autowired
//     private EmailService emailService;
//
//    // Sending a simple Email
//    @PostMapping("/sendMail")
//    public String
//    sendMail(@RequestBody EmailDetails details)
//    {
//        String status
//                = emailService.sendSimpleMail(details);
//
//        return status;
//    }
//
//}
