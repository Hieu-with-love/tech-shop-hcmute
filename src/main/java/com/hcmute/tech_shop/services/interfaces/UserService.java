package com.hcmute.tech_shop.services.interfaces;

import com.hcmute.tech_shop.dtos.requests.PasswordRequest;
import com.hcmute.tech_shop.dtos.requests.ProfileDto;
import com.hcmute.tech_shop.dtos.requests.ProfileRequest;
import com.hcmute.tech_shop.dtos.requests.UserRequest;
import com.hcmute.tech_shop.entities.Address;
import com.hcmute.tech_shop.entities.User;
import org.apache.coyote.BadRequestException;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

public interface UserService {
    List<User> getAllUsers();
    User getUser(Long id);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    boolean createUser(UserRequest userRequest, MultipartFile file, BindingResult result) throws IOException;
    Address saveAddress(Map<String, String> params);
    boolean updateUser(Long id, UserRequest userRequest, BindingResult result);
    boolean updateProfile(User user, ProfileDto profileDto, MultipartFile file, BindingResult result);
    boolean updatePassword(Map<String, String> params, BindingResult result);
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
