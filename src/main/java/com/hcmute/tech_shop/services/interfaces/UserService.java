package com.hcmute.tech_shop.services.interfaces;

import com.hcmute.tech_shop.dtos.requests.UserDTO;
import com.hcmute.tech_shop.entities.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUser(Long id);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    User createUser(UserDTO userDTO);
    User updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
    boolean existsEmail(String email);
    boolean existsUsername(String username);
    boolean verifyToken(String token);
}
