package com.hcmute.tech_shop.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class Constant {
    public static Locale vietnam = Locale.of("vi", "VN");
    // Định dạng số thành tiền tệ Việt Nam Đồng
    public static NumberFormat formatter = NumberFormat.getCurrencyInstance(vietnam);
}
