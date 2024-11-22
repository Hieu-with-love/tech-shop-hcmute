package com.hcmute.tech_shop.controllers;

import com.hcmute.tech_shop.dtos.requests.UserRequest;
import com.hcmute.tech_shop.dtos.responses.CartDetailResponse;
import com.hcmute.tech_shop.dtos.responses.ProductResponse;
import com.hcmute.tech_shop.entities.*;
import com.hcmute.tech_shop.services.interfaces.*;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.services.interfaces.IProductService;
import com.hcmute.tech_shop.services.interfaces.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final IProductService productService;
    private final ICategoryService categoryService;
    private final CartService cartService;
    private final ICartDetailService cartDetailServiceImpl;

    public UserRequest getUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        UserRequest userRequest = UserRequest.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .dob(user.getDateOfBirth())
                .active(user.isActive())
                .image(user.getImage())
                .build();
        return userRequest;
    }

    @GetMapping("/home")
    public String home(Model model,
                       HttpSession session,
                       @RequestParam(defaultValue = "0") int pageNumber,
                       @RequestParam(defaultValue = "12") int pageSize,
                       @RequestParam(defaultValue = "sort") String sortBy,
                       @RequestParam(defaultValue = "true") boolean ascending) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Page<Product> currentPage = productService.getAllProducts(pageNumber, pageSize);
        List<Product> products = currentPage.getContent();
        List<ProductResponse> productResponses = productService.getAllProducts(products);
        model.addAttribute("products", productResponses);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", currentPage.getTotalPages());

        List<Category> categories = categoryService.findAll();
        List<CartDetailResponse> cartDetailList = new ArrayList<>();

        int numberProductInCart = 0;

        Cart cart = new Cart();
        if(!username.equals("anonymousUser")) {
            User user = userService.getUserByUsername(username);
            UserRequest userRequest = getUser();
            cart = cartService.findByCustomerId(userRequest.getId());
            if(cart == null) {
                cart = cartService.createCart(new Cart(null,BigDecimal.ZERO,userRequest.getId(),null));
            }
            cartDetailList = cartDetailServiceImpl.getAllItems(cartDetailServiceImpl.findAllByCart_Id(cart.getId()));
            numberProductInCart = cartDetailList.size();
            if(cartDetailList.size() > 3) {
                cartDetailList = cartDetailList.subList(0, 3);
            }
            session.setAttribute("user", user);
        }

        model.addAttribute("categories", categories);
        model.addAttribute("cart", cart);
        model.addAttribute("cartDetailList", cartDetailList);
        model.addAttribute("numberProductInCart", numberProductInCart);

        session.setAttribute("cart", cart);
        session.setAttribute("cartDetailList", cartDetailList);
        session.setAttribute("numberProductInCart", numberProductInCart);
        return "user/home";
    }

    @GetMapping("/not-found")
    public String notFound(Model model) {
        return "user/404";
    }

    @GetMapping("/my-account")
    public String showProfile(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        com.hcmute.tech_shop.entities.User user = userService.getUserByUsername(username);
        UserRequest userRequest = UserRequest.builder()
                .id(user.getId())
                .username(username)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .dob(user.getDateOfBirth())
                .active(user.isActive())
                .image(user.getImage())
                .build();
        model.addAttribute("userDto", userRequest);
        return "user/my-account";
    }

    @PostMapping("/my-account")
    public String updateProfile(Model model,
                                @Valid @ModelAttribute("userDto") UserRequest userDto,
                                BindingResult result) {
        if (result.hasErrors()){
            model.addAttribute("error", result.getAllErrors());
            return "user/my-account";
        }

        boolean isSuccess = userService.updateProfile(userDto.getUsername(), userDto, result);
        if (isSuccess){
            model.addAttribute("msg", "Doi thong tin tai khoan thanh cong !");
        }else{
            model.addAttribute("msg", "Doi thong tin tai khoan that bai !");
        }
        return "user/my-account";
    }



    // Làm chức năng gì thì đặt endpoint là, /user/<name-feature>
    /*
        Ví dụ: Làm chức năng chi tiết sản phẩm (detail-cart)
        -> Đặt tên: @GetMapping("/user/detail/cart") nhưng ở trên có "/user" rồi
        -> còn lại là @GetMapping("/detail/cart")
    * */
}
