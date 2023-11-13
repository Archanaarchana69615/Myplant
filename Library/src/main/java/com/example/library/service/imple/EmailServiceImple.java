package com.example.library.service.imple;

import com.example.library.model.EmailDetails;
import com.example.library.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Value;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImple implements EmailService {

    // Java Program to Illustrate Creation Of
// Service implementation class

    @Autowired
    private JavaMailSender javaMailSender;

//    JavaMailSender javaMailSender = new JavaMailSenderImpl();

//    @Value(staticConstructor = "archanaarchana69615@gmail.com")
     String sender="archanaarchana69615@gmail.com";

    // Method 1
    // To send a simple email
    public String sendSimpleMail(EmailDetails details) {

        // Try block to check for exceptions
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
//    @Override
//    public String sendMailWithAttachment(EmailDetails details) {
//        // Creating a mime message
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper mimeMessageHelper;
//
//        try {
//            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//            mimeMessageHelper.setFrom(sender);
//            mimeMessageHelper.setTo(details.getRecipient());
//            mimeMessageHelper.setText(details.getMsgBody());
//            mimeMessageHelper.setSubject(details.getSubject());
//
//            // Adding the attachment
//            FileSystemResource file = new FileSystemResource(new File(details.getMsgBody()));
//            mimeMessageHelper.addAttachment(file.getFilename(), file);
//
//            // Sending the mail
//            javaMailSender.send(mimeMessage);
//            return "Mail sent Successfully";
//        } catch (MessagingException e) {
//            // Display an error message when an exception occurs
//            return "Error while sending mail!!!";
//        }
//    }


}
