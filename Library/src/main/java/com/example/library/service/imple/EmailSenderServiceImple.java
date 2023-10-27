package com.example.library.service.imple;

import com.example.library.model.Email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.example.library.service.EmailSenderService;

import java.io.File;

@Service
public class EmailSenderServiceImple implements EmailSenderService {

     private JavaMailSender javaMailSender;

   @Value("${spring.mail.username}") private String sender;


    public EmailSenderServiceImple(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public String sendSimpleMail(Email email) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();


            mailMessage.setFrom(sender);
            mailMessage.setTo(email.getRecipient());
            mailMessage.setText(email.getMessagebody());
            mailMessage.setSubject(email.getSubject());

            javaMailSender.send(mailMessage);
            return "Mail send successfully";
        } catch (Exception e) {
            return "Error while sending mail";
        }
    }

    @Override
    public String sendMailWithAttachment(Email email) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(email.getRecipient());
            mimeMessageHelper.setTo(email.getMessagebody());
            mimeMessageHelper.setSubject(email.getSubject());


            FileSystemResource file = new FileSystemResource(new File(email.getAttachement()));

            mimeMessageHelper.addAttachment(file.getFilename(), file);


            javaMailSender.send(mimeMessage);
            return "Mail sent successfully";

        } catch (MessagingException e) {
            return "Error while sending mail!!!";
        }

    }
}
