package com.hcmute.tech_shop.services.interfaces;

import com.hcmute.tech_shop.dtos.requests.UserRequest;
import com.hcmute.tech_shop.entities.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUser(Long id);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    User createUser(UserRequest userRequest);
    User updateUser(Long id, UserRequest userRequest);
    void deleteUser(Long id);
    boolean existsEmail(String email);
    boolean existsUsername(String username);
    boolean verifyToken(String token);
}
