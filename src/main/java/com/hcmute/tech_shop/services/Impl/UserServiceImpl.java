package com.hcmute.tech_shop.services.Impl;

import com.hcmute.tech_shop.dtos.requests.UserRequest;
import com.hcmute.tech_shop.entities.Confirmation;
import com.hcmute.tech_shop.entities.Role;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.repositories.ConfirmationRepository;
import com.hcmute.tech_shop.repositories.UserRepository;
import com.hcmute.tech_shop.services.interfaces.CartService;
import com.hcmute.tech_shop.services.interfaces.EmailService;
import com.hcmute.tech_shop.services.interfaces.RoleService;
import com.hcmute.tech_shop.services.interfaces.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    CartService cartService;
    RoleService roleService;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    ConfirmationRepository confirmationRepository;
    EmailService emailService;

    private void validation(UserRequest userRequest, BindingResult result){
        // Kiem tra username da ton tai chua?
        if (this.existsUsername(userRequest.getUsername())) {
            result.addError(new FieldError("userRegister", "username",
                    "Username da ton tai. Vui long nhap username khac"));
        }
        if (this.existsEmail(userRequest.getEmail())) {
            result.addError(new FieldError("userRegister", "email",
                    "Email da ton tai. Vui long nhap Email khac"));
        }

        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            result.addError(new FieldError("registerUser", "password",
                    "Mat khau khong khop"));
        }

        // Lấy day/month/year hiện tại, 11/2/2024 -> tru 15 năm -> 11/2/2009
        // giả sử một người có sinh nhật 1/2/2009 -> 1/2/2009 đã đu tuổi so voi day/month/year hiện tại
        // nên dùng isBefore (truoc rồi phủ định) chứ không dùng isAfter
        if (!userRequest.getDob().isBefore(LocalDate.now().minusYears(15))){
            result.addError(new FieldError("userRegister", "dob",
                    "Bạn chưa đủ 15 tuổi"));
        }
    }

    @Override
    public boolean createUser(UserRequest userRequest, BindingResult result) {
        Role role = roleService.getRoleByName("user");

        validation(userRequest, result);

        if (!result.hasErrors()) {
            User user = User.builder()
                    .username(userRequest.getUsername())
                    .email(userRequest.getEmail())
                    .phoneNumber(userRequest.getPhoneNumber())
                    .firstName(userRequest.getFirstName())
                    .lastName(userRequest.getLastName())
                    .gender(userRequest.getGender())
                    .dateOfBirth(userRequest.getDob())
                    .role(role)
                    .isActive(false)
                    .build();
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            userRepository.save(user);

            Confirmation confirm = Confirmation.builder()
                    .user(user)
                    .token(UUID.randomUUID().toString())
                    .createDate(LocalDateTime.now())
                    .build();
            confirmationRepository.save(confirm);

            // TODO Send email to user with token, using SimpleMailSender
            try{
                emailService.sendEmailToVerifyAccount(user.getUsername(), user.getEmail(),
                        confirm.getToken()
                );
            }catch (Exception e){
                throw new RuntimeException("Send email failed\n\n" + e.getMessage());
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(Long id, UserRequest userRequest, BindingResult result) {
        // logic update user
        validation(userRequest, result);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return false;
    }

    @Override
    public boolean verifyToken(String token) {
        try{
            Confirmation confirm = confirmationRepository.findByToken(token)
                    .orElseThrow(() -> new RuntimeException("Confirmation token not found at verify token"));
            User user = userRepository.findByUsernameIgnoreCase(confirm.getUser().getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found at verify token"));
            user.setActive(true);
            userRepository.save(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id error"));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Not found User with username " + username));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("Not found User with email " + email));
    }

    @Override
    public void deleteUser(Long id) {
        User existingUser = this.getUser(id);
        if (existingUser != null) {
            existingUser.setActive(false);
            userRepository.save(existingUser);
        }
    }

    @Override
    public boolean existsUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsEmail(String email) {
        return userRepository.existsByEmail(email);
    }


}
