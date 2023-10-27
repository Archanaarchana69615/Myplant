package com.example.customer.controller;

import com.example.library.model.Email;
import com.example.library.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailSenderService emailSenderService;

   @PostMapping("/sendMail")
    public String
    sendMail(@RequestBody Email email)
   {
       String status= emailSenderService.sendSimpleMail(email);

       return status;
   }
   @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(@RequestBody Email email)
   {
       String status=emailSenderService.sendMailWithAttachment(email);
       return status;
   }
}
