//package com.example.customer.controller;
//
//import com.example.library.dto.CustomerDto;
//import com.example.library.service.CustomerService;
//import com.example.library.service.imple.CustomerNotFoundException;
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import jakarta.servlet.http.HttpServletRequest;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.repository.query.Param;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.io.UnsupportedEncodingException;
//
//@Controller
//public class ForgotPasswordController {
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @Autowired
//    private CustomerService customerService;
//
//    @GetMapping("/forgot_password")
//    public String showForgotPasswordForm() {
//
//        return "enterUsername";
//    }
//
//    @PostMapping("/forgot_password")
//    public String processForgotPassword(@RequestParam HttpServletRequest request, Model model) {
//        String email = request.getParameter("email");
//        String token = RandomStringUtils.randomAlphanumeric(30);
//        try {
//            customerService.updateResetPasswordTocken(token,email);
//            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
//            sendEmail(email, resetPasswordLink);
//            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
//
//        } catch (CustomernotFoundException ex) {
//            model.addAttribute("error", ex.getMessage());
//        } catch (Exception e) {
//            model.addAttribute("error", "Error while sending email");
////        } catch (CustomerNotFoundException e) {
////            throw new RuntimeException(e);
//        }
//
//        return "forgot_password";
//    }
//    public void sendEmail(String recipientEmail, String link)
//            throws MessagingException, UnsupportedEncodingException {
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message);
//
//        helper.setFrom("contact@shopme.com", "Shopme Support");
//        helper.setTo(recipientEmail);
//
//        String subject = "Here's the link to reset your password";
//
//        String content = "<p>Hello,</p>"
//                + "<p>You have requested to reset your password.</p>"
//                + "<p>Click the link below to change your password:</p>"
//                + "<p><a href=\"" + link + "\">Change my password</a></p>"
//                + "<br>"
//                + "<p>Ignore this email if you do remember your password, "
//                + "or you have not made the request.</p>";
//
//        helper.setSubject(subject);
//
//        helper.setText(content, true);
//
//        mailSender.send(message);
//    }
//
////    @GetMapping("/reset_password")
////    public String showResetPasswordForm(@Param(value = "tocken")String tocken,Model model) {
////     Customer customer = customerService.
////    }
////
////    @PostMapping("/reset_password")
////    public String processResetPassword() {
////
////    }
//}
