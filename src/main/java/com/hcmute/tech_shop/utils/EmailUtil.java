package com.hcmute.tech_shop.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class EmailUtil {
    public static String getEmailMessage(String name, String host, String token){
        return "Hello " + name
                + ",\n\n Your account has been created. Please click on the link below to verify your account\n\n"
                + getVerificationUrl(host, token)
                + "\n\n The support by [4 con ong Team]";
    }

    public static String getVerificationUrl(String host, String token){
        return host + "/verify-account?token=" + token;
    }

    public static String generateRandomPassword(){
        int length = 8;
        return RandomStringUtils.randomAlphanumeric(length);
    }
}
