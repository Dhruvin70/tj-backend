package com.project.tj.com.project.tj.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String email,String otp) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setSubject("OTP Verification from TJ");
        mail.setText(otp);

        javaMailSender.send(mail);
    }
}
