package com.example.library.service;

import com.example.library.model.EmailDetails;
import org.springframework.stereotype.Service;


@Service
public interface EmailService {
    String sendSimpleMail(EmailDetails details);


}
