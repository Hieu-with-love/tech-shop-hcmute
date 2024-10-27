package com.hcmute.tech_shop.services.classes;

import com.hcmute.tech_shop.dtos.requests.UserDTO;
import com.hcmute.tech_shop.entities.Cart;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.enums.Role;
import com.hcmute.tech_shop.repositories.UserRepository;
import com.hcmute.tech_shop.services.interfaces.CartService;
import com.hcmute.tech_shop.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final CartService cartService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserDTO userDTO) {
        User user = User.builder()
                .email(userDTO.getEmail())
                .phoneNumber(userDTO.getPhoneNumber())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .gender(userDTO.getGender())
                .dateOfBirth(userDTO.getDob())
                .active(true)
                .build();
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);

        userRepository.save(user);
        return user;
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
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Not found User with email " + email));
    }

    @Override
    public User updateUser(Long id, UserDTO userDTO) {
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
    public boolean existsUser(String email) {
        return userRepository.existsByEmail(email);
    }
}
