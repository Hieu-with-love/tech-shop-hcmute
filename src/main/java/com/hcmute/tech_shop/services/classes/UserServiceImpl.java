package com.hcmute.tech_shop.services.classes;

import com.hcmute.tech_shop.dtos.requests.UserDTO;
import com.hcmute.tech_shop.entities.Cart;
import com.hcmute.tech_shop.entities.Role;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.repositories.UserRepository;
import com.hcmute.tech_shop.services.interfaces.CartService;
import com.hcmute.tech_shop.services.interfaces.RoleService;
import com.hcmute.tech_shop.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RoleService roleService;
    private final CartService cartService;
    private final UserRepository userRepository;

    @Override
    public User createUser(UserDTO userDTO) {
        Role role =  roleService.getRoleById(userDTO.getRoleId());
        User user = User.builder()
                .email(userDTO.getEmail())
                .phoneNumber(userDTO.getPhoneNumber())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .active(true)
                .role(role)
                .build();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
        return user;
    }
}
