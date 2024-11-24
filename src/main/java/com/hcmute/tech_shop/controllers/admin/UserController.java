package com.hcmute.tech_shop.controllers.admin;

import com.hcmute.tech_shop.dtos.requests.ProfileRequest;
import com.hcmute.tech_shop.dtos.requests.UserRequest;
import com.hcmute.tech_shop.entities.Role;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.services.interfaces.RoleService;
import com.hcmute.tech_shop.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller("userControllerOfAdmin")
@RequestMapping("/admin/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    private void addRolesToModel(Model model) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
    }

    @GetMapping("")
    public String userlist(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users/userlists";
    }

    @GetMapping(value = "/add-user")
    public String addUser(Model model) {
        addRolesToModel(model);
        UserRequest userRequest = new UserRequest();
        model.addAttribute("userRequest", userRequest);
        return "admin/users/newuser";
    }

    @PostMapping("/add-user")
    public String saveUser(@Valid @ModelAttribute("userRequest") UserRequest userRequest,
                           BindingResult bindingResult,
                           @RequestParam("file") MultipartFile file,
                           Model model) {
        if (bindingResult.hasErrors()) {
            addRolesToModel(model);
            return "admin/users/newuser";
        }

        if (userService.existsUsername(userRequest.getUsername())) {
            addRolesToModel(model);
            model.addAttribute("error", "Username đã tồn tại!");
            return "admin/users/newuser";
        }

        if (userService.existsEmail(userRequest.getEmail())) {
            addRolesToModel(model);
            model.addAttribute("error", "Email đã được sử dụng!");
            return "admin/users/newuser";
        }

        if(!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            addRolesToModel(model);
            model.addAttribute("error", "Mật khẩu không khơp");
            return "admin/users/newuser";
        }

        userService.saveUser(userRequest, file);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {


        User user = userService.getUser(id);

        if (user == null) {
            throw new RuntimeException("User không tồn tại!");
        }
        ProfileRequest profileRequest = new ProfileRequest();
        BeanUtils.copyProperties(user, profileRequest);

        profileRequest.setRoleId(user.getRole() != null ? user.getRole().getId() : null);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = user.getDateOfBirth() != null ? user.getDateOfBirth().format(formatter) : "";

        addRolesToModel(model);
        model.addAttribute("profileRequest", profileRequest);
        model.addAttribute("formattedDateOfBirth", formattedDate);

        return "admin/users/edituser";
    }


    @PostMapping("/edit")
    public String editUser(@Valid @ModelAttribute("profileRequest") ProfileRequest profileRequest,
                           BindingResult bindingResult,
                           @RequestParam("imageFile") MultipartFile file,
                           Model model) {

        if (bindingResult.hasErrors()) {
            addRolesToModel(model);
            return "admin/users/edituser";
        }

        User userByUsername = userService.getUserByUsername(profileRequest.getUsername());
        if (userByUsername != null && !userByUsername.getId().equals(profileRequest.getId())) {
            addRolesToModel(model);
            model.addAttribute("error", "Username đã tồn tại!");
            return "admin/users/edituser";
        }

        User userByEmail = userService.getUserByEmail(profileRequest.getEmail());
        if (userByEmail != null && !userByEmail.getId().equals(profileRequest.getId())) {
            addRolesToModel(model);
            model.addAttribute("error", "Email đã được sử dụng bởi người khác!");
            return "admin/users/edituser";
        }


        try {
            userService.updateProfile(profileRequest, file);
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra khi cập nhật thông tin người dùng.");
            addRolesToModel(model);
            return "admin/users/edituser";
        }

        return "redirect:/admin/users";
    }


    @GetMapping("/password/{id}")
    public String passWord(@PathVariable("id") Long id, Model model) {
        User user = userService.getUser(id);
        addRolesToModel(model);
        model.addAttribute("user", user);
        return "admin/users/editpassword";
    }
}
