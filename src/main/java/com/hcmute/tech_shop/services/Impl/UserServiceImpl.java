package com.hcmute.tech_shop.services.Impl;

import com.hcmute.tech_shop.dtos.requests.PasswordRequest;
import com.hcmute.tech_shop.dtos.requests.ProfileDto;
import com.hcmute.tech_shop.dtos.requests.ProfileRequest;
import com.hcmute.tech_shop.dtos.requests.UserRequest;
import com.hcmute.tech_shop.entities.Address;
import com.hcmute.tech_shop.entities.Confirmation;
import com.hcmute.tech_shop.entities.Role;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.utils.ImageUtil;
import com.hcmute.tech_shop.repositories.ConfirmationRepository;
import com.hcmute.tech_shop.repositories.RoleRepository;
import com.hcmute.tech_shop.repositories.UserRepository;
import com.hcmute.tech_shop.services.interfaces.CartService;
import com.hcmute.tech_shop.services.interfaces.EmailService;
import com.hcmute.tech_shop.services.interfaces.RoleService;
import com.hcmute.tech_shop.services.interfaces.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
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
    AddressServiceImpl addressService;
    private final ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepository;

    private void validation(UserRequest userRequest, BindingResult result){
        // Kiem tra username da ton tai chua?

        if (this.existsUsername(userRequest.getUsername())) {
            result.addError(new FieldError("userDto", "username",
                    "Username da ton tai. Vui long nhap username khac"));
        }
        if (this.existsEmail(userRequest.getEmail())) {
            result.addError(new FieldError("userDto", "email",
                    "Email da ton tai. Vui long nhap Email khac"));
        }

        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            result.addError(new FieldError("userRegister", "password",
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
    public boolean createUser(UserRequest userRequest, BindingResult result) throws IOException {
        Role role = roleService.getRoleByName("user");

        validation(userRequest, result);

        if (!result.hasErrors()) {

//            String avatar = null;
//            if (file == null){
//                avatar = "avtdefault.jpg";
//            } else {
//                if (!ImageUtil.isValidSuffixImage(Objects.requireNonNull(file.getOriginalFilename()))) {
//                    throw new BadRequestException("Image is not valid");
//                }
//                avatar = ImageUtil.saveImage(file);
//            }

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
    public Address saveAddress(Map<String, String> params) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("Don't have user")
        );
        String city = params.get("city");
        String district = params.get("district");
        String street = params.get("street");
        String detailLocation = params.get("detailLocation");
        Address address = Address.builder()
                .city(city)
                .district(district)
                .street(street)
                .detailLocation(detailLocation)
                .user(user)
                .build();

        // Thêm địa chỉ vào danh sách địa chỉ của User
        user.getAddresses().add(address);

        // Lưu user (cascade sẽ tự lưu address nếu được cấu hình đúng)
        userRepository.save(user);
        return address;
    }

    @Override
    public boolean updateUser(Long id, UserRequest userRequest, BindingResult result) {

        return false;
    }

    @Override
    public boolean updateProfile(User user, ProfileDto profileDto, MultipartFile file, BindingResult result) {
        // logic update user
        if (!user.getUsername().equals(profileDto.getUsername()) && this.existsUsername(profileDto.getUsername())) {
            result.addError(new FieldError("userRequest", "username",
                    "Username da ton tai. Vui long nhap username khac"));
        }

        if (!user.getEmail().equals(profileDto.getEmail()) && this.existsEmail(profileDto.getEmail())){
            result.addError(new FieldError("userRequest", "username",
                    "Username da ton tai. Vui long nhap username khac"));
        }

        if (!profileDto.getDob().isBefore(LocalDate.now().minusYears(15))){
            result.addError(new FieldError("userRegister", "dob",
                    "Bạn chưa đủ 15 tuổi"));
        }

        if (result.hasErrors()) {
            return false;
        }

        try{
            String avatar = null;
            if (file == null){
                avatar = "avtdefault.jpg";
            } else {
                if (!ImageUtil.isValidSuffixImage(Objects.requireNonNull(file.getOriginalFilename()))) {
                    throw new BadRequestException("Image is not valid");
                }
                if (ImageUtil.deleteImage(profileDto.getImage())) {
                    avatar = ImageUtil.saveImage(file);
                }
            }

            User existingUser = userRepository.findByUsernameIgnoreCase(user.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            existingUser.setFirstName(profileDto.getFirstName());
            existingUser.setLastName(profileDto.getLastName());
            existingUser.setGender(profileDto.getGender());
            existingUser.setEmail(profileDto.getEmail());
            existingUser.setPhoneNumber(profileDto.getPhone());
            existingUser.setGender(profileDto.getGender());
            existingUser.setDateOfBirth(profileDto.getDob());
            existingUser.setImage(avatar);

            userRepository.save(existingUser);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean updatePassword(Map<String,String> params, BindingResult result) {
        String oldPassword = params.get("old-password");
        String password = params.get("password");
        String username = params.get("username");
        User user = this.getUserByUsername(username);
        // Kiểm tra mật khẩu trống hoặc độ dài ngắn
        if (password == null || password.length() < 3) {
            result.addError(new FieldError("changePassword", "password", "Password phải có ít nhất 3 ký tự."));
        }

        // Kiểm tra xác nhận mật khẩu
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            result.addError(new FieldError("changePassword", "confirmPassword", "Xác nhận mật khẩu không khớp."));
        }

        if (!result.hasErrors()) {
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            return true;
        }
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
    public UserRequest convertToDto(User user) {
        return modelMapper.map(user, UserRequest.class);
    }

    @Override
    public User convertToUser(UserRequest userRequest) {
        return modelMapper.map(userRequest, User.class);
    }

    @Override
    public void saveUser(UserRequest userRequest, MultipartFile file) {
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);

        user.setDateOfBirth(userRequest.getDob());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(roleService.getRoleById(userRequest.getRoleId()));

        if (file != null && !file.isEmpty()) {
            if (!ImageUtil.isValidSuffixImage(file.getOriginalFilename())) {
                throw new IllegalArgumentException("File không đúng định dạng ảnh!");
            }
            try {
                String savedImageName = ImageUtil.saveImage(file);
                user.setImage(savedImageName);
            } catch (IOException e) {
                throw new RuntimeException("Lỗi khi lưu ảnh: " + e.getMessage());
            }
        } else {
            user.setImage("avtdefault.jpg");
        }
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
    }

    @Override
    public void updateProfile(ProfileRequest profileRequest, MultipartFile file) {
        User user = new User();
        BeanUtils.copyProperties(profileRequest, user);

        user.setDateOfBirth(profileRequest.getDateOfBirth());
        user.setRole(roleService.getRoleById(profileRequest.getRoleId()));

        if (file != null && !file.isEmpty()) {
            if (!ImageUtil.isValidSuffixImage(file.getOriginalFilename())) {
                throw new IllegalArgumentException("File không đúng định dạng ảnh!");
            }

            try {
                if (user.getImage() != null && !user.getImage().isEmpty() && !user.getImage().equals("avtdefault.jpg")) {
                    ImageUtil.deleteImage(user.getImage());
                }
                String savedImageName = ImageUtil.saveImage(file);
                user.setImage(savedImageName);
            } catch (IOException e) {
                throw new RuntimeException("Lỗi khi lưu ảnh: " + e.getMessage());
            }
        }
        userRepository.save(user);
    }

    @Override
    public void editPassword(PasswordRequest passwordRequest) {
        User user = userRepository.findById(passwordRequest.getId())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        String encodedPassword = passwordEncoder.encode(passwordRequest.getNewPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
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
        if ("anonymousUser".equals(username)){
            return null;
        }
        return userRepository.findByUsername(username)
                .orElse(null);
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
