package com.hcmute.tech_shop.services.interfaces;

public interface EmailService {
    void sendEmailToVerifyAccount(String name, String to, String token);
    void sendEmailToReactivePassword(String email);
}
