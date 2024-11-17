package com.hcmute.tech_shop.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hcmute.tech_shop.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    Long id;

    @NotNull(message = "Username là bắt buộc!")
    String username;

    @NotBlank(message = "Email là bắt buộc!")
    @Email(message = "Email nên chứa kí tự @.\n Ex: name@gmail.com")
    String email;

    @NotBlank(message = "Nhập mật khẩu!")
    String password;

    @NotNull(message = "Nhập mật khẩu xác nhận!")
    @JsonProperty("confirm_password")
    String confirmPassword;

    @JsonProperty("phone_number")
    String phoneNumber;

    @NotBlank(message = "Nhập tên của bạn và tên lót nếu có!")
    @JsonProperty("firstname")
    String firstName;

    @NotBlank(message = "Nhập họ của bạn!")
    String lastName;

    String gender;

    @NotNull(message = "Nhập ngày tháng năm sinh!")
    @Future(message = "Ngay sinh khong the o tuong lai")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dob;

    boolean active;

    String image;

    Long roleId;

}
