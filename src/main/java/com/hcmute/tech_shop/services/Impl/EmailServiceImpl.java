package com.hcmute.tech_shop.services.Impl;

import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.repositories.UserRepository;
import com.hcmute.tech_shop.services.interfaces.EmailService;
import com.hcmute.tech_shop.services.interfaces.UserService;
import com.hcmute.tech_shop.utils.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
            throw new RuntimeException("Has error occurred while sending email to verify account\n\n"
                    + e.getMessage());
        }
    }

    @Override
    public void sendEmailToReactivePassword(String email) {
        User existingUser = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("User not found at send email get password"));
        String newPassword = EmailUtil.generateRandomPassword();
        existingUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(existingUser);
        try{
            String contentMail = "New your password is: " + newPassword
                                + "\n\nThe support by [4 con ong team]";
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Provide New Password");
            message.setFrom(fromEmail);
            message.setTo(existingUser.getEmail());
            message.setText(contentMail);
            mailSender.send(message);
        }catch (Exception e){
            throw new RuntimeException("Has error occurred while sending email to get password\n\n"
                    + e.getMessage());
        }
    }
}
