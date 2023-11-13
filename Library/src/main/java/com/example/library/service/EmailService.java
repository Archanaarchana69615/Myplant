package com.example.library.service;

import com.example.library.dto.RegisterDto;
import com.example.library.model.EmailDetails;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface EmailService {
    String sendSimpleMail(EmailDetails details);

//    String sendMailWithAttachment(EmailDetails details);

//    public default String register(RegisterDto registerDto)
//    {
//        return null;
//    }
}
