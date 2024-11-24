package com.hcmute.tech_shop.services.interfaces;

import com.hcmute.tech_shop.dtos.requests.PasswordRequest;
import com.hcmute.tech_shop.dtos.requests.ProfileRequest;
import com.hcmute.tech_shop.dtos.requests.UserRequest;
import com.hcmute.tech_shop.entities.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUser(Long id);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    boolean createUser(UserRequest userRequest, BindingResult result);
    boolean updateUser(Long id, UserRequest userRequest, BindingResult result);
    boolean updateProfile(String username, UserRequest userRequest, BindingResult result);
    boolean updatePassword(Long id, String password, String confirmPassword, BindingResult result);
    void deleteUser(Long id);
    boolean existsEmail(String email);
    boolean existsUsername(String username);
    boolean verifyToken(String token);
    UserRequest convertToDto(User user);
    User convertToUser(UserRequest userRequest);
    void saveUser(UserRequest userRequest, MultipartFile file);
    void updateProfile(ProfileRequest profileRequest, MultipartFile file);
    void editPassword(PasswordRequest passwordRequest);
}
