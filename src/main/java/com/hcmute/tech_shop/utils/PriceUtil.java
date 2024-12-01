package com.hcmute.tech_shop.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class PriceUtil {
    private static final BigDecimal EXCHANGE_RATE = new BigDecimal("24000.00");

    // Hàm chuyển đổi từ VND sang USD
    public static BigDecimal convertVNDToUSD(BigDecimal amountInVND) {
        return amountInVND.divide(EXCHANGE_RATE, 2, RoundingMode.HALF_UP); // Làm tròn đến 2 chữ số thập phân
    }

    // Format price to 000,000,000.00
    public static String formatPrice(BigDecimal price) {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        return formatter.format(price);
    }
}
