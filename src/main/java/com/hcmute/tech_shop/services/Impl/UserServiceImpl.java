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

import java.time.LocalDateTime;
import java.util.HashSet;
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


    @Override
    public User createUser(UserRequest userRequest) {
        Role role = roleService.getRoleByName("user");
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

        return user;
    }

    @Override
    public boolean verifyToken(String token) {
        Confirmation confirm = confirmationRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Confirmation token not found at verify token"));
        User user = userRepository.findByUsernameIgnoreCase(confirm.getUser().getUsername())
                .orElseThrow(() -> new RuntimeException("User not found at verify token"));
        user.setActive(true);
        userRepository.save(user);

        return true;
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
    public User updateUser(Long id, UserRequest userRequest) {
        // logic update user
        return null;
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
