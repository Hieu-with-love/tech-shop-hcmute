package com.hcmute.tech_shop.dtos.responses;

import com.hcmute.tech_shop.entities.Address;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderReponse {
    private Long orderId;
    private User shipper;
    private OrderStatus status;
    private String customerName;
    private String paymentName;
    private String totalPrice;
    private Address shippingAddress;
}