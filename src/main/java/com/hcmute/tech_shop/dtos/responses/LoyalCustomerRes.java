package com.hcmute.tech_shop.dtos.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class LoyalCustomerRes {
    private String fullName;
    private String phone;
    private String email;
    private LocalDate dateOfBirth;
    private String totalPurchaseDue;
    private String image;

    public LoyalCustomerRes(String fullName, String phone, String email, LocalDate dateOfBirth, String totalPurchaseDue, String image) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.totalPurchaseDue = totalPurchaseDue;
        this.image = image;
    }

}
