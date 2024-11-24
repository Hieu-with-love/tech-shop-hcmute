package com.hcmute.tech_shop.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileRequest {
    Long id;

    @NotNull(message = "Username là bắt buộc!")
    String username;

    @NotBlank(message = "Email là bắt buộc!")
    @Email(message = "Email nên chứa kí tự @.\n Ex: name@gmail.com")
    String email;

    @JsonProperty("phone_number")
    String phoneNumber;

    @NotBlank(message = "Nhập tên của bạn và tên lót nếu có!")
    String firstName;

    @NotBlank(message = "Nhập họ của bạn!")
    String lastName;

    String gender;

    @NotNull(message = "Nhập ngày tháng năm sinh!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dateOfBirth;

    @NotNull(message = "Trạng thái active là bắt buộc!")
    boolean isActive;

    private String password;

    String image;

    Long roleId;
}
