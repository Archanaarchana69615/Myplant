package com.example.library.service;
import com.example.library.model.Email;

public interface EmailSenderService {

    String sendSimpleMail(Email email);


    String sendMailWithAttachment(Email email);
}
