//package com.example.customer.controller;
//
//import com.twilio.Twilio;
//import com.twilio.rest.chat.v2.service.channel.Message;
//import com.twilio.type.PhoneNumber;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@RestController
//public class SmsController {
//    @GetMapping(value = "/sendSMS")
//    public ResponseEntity<String> sendSMS()
//    {
//        Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"),System.getenv("TWILO_AUTH_TOKEN"));
//
//        Message.creator(new com.twilio.type.PhoneNumber("+916235689401"),
//                new com.twilio.type.PhoneNumber("+13144037148")).create();
//
//
//
//        return new ResponseEntity<String>("Message send successfully", HttpStatus.OK);
//    }
//
//}
