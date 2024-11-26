package com.hcmute.tech_shop.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PriceUtil {
    private static final BigDecimal EXCHANGE_RATE = new BigDecimal("24000.00");

    // Hàm chuyển đổi từ VND sang USD
    public static BigDecimal convertVNDToUSD(BigDecimal amountInVND) {
        return amountInVND.divide(EXCHANGE_RATE, 2, RoundingMode.HALF_UP); // Làm tròn đến 2 chữ số thập phân
    }
}
