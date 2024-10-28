package com.hcmute.tech_shop.services.Impl;

import com.hcmute.tech_shop.services.interfaces.EmailService;
import com.hcmute.tech_shop.utils.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;
    private final JavaMailSender mailSender;

    @Override
    public void sendEmailToVerifyAccount(String name, String to, String token) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("New User Account Verification");
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText(EmailUtil.getEmailMessage(name, host, token));
            mailSender.send(message);
        }catch (Exception e){
            throw new RuntimeException("Has error occured while sending email\n\n" + e.getMessage());
        }
    }
}
