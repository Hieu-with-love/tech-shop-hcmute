package com.hcmute.tech_shop.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Cấu hình Spring Boot phục vụ file từ thư mục "uploads"
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/"); // Đường dẫn tới thư mục "uploads" trên hệ thống file
    }
}
