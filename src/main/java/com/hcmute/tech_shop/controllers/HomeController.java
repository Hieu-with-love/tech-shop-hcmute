package com.hcmute.tech_shop.controllers;

import com.hcmute.tech_shop.dtos.requests.ProfileDto;
import com.hcmute.tech_shop.dtos.requests.UserRequest;
import com.hcmute.tech_shop.dtos.responses.CartDetailResponse;
import com.hcmute.tech_shop.dtos.responses.ProductResponse;
import com.hcmute.tech_shop.entities.*;
import com.hcmute.tech_shop.services.Impl.AddressServiceImpl;
import com.hcmute.tech_shop.services.interfaces.*;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.services.interfaces.IProductService;
import com.hcmute.tech_shop.services.interfaces.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final IProductService productService;
    private final ICategoryService categoryService;
    private final CartService cartService;
    private final ICartDetailService cartDetailServiceImpl;
    private final WishlistService wishlistService;
    private final WishlistItemService wishlistItemService;
    private final AddressServiceImpl addressService;
    private final IBrandService brandService;

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

        List<Brand> brands = brandService.findAll();
        model.addAttribute("brands", brands);

        List<Category> categories = categoryService.findAll();
        List<CartDetailResponse> cartDetailList = new ArrayList<>();

        int numberProductInCart = 0;

        Cart cart = new Cart();
        if(!username.equals("anonymousUser")) {
            User user = userService.getUserByUsername(username);
            wishlistService.createWishlist(user);
            Wishlist wishlist = wishlistService.getWishlistByUserId(user.getId());
            int wishlistItems = wishlistItemService.getItemsCount(wishlist.getId());

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
            session.setAttribute("wishlistId", wishlist.getId());
            session.setAttribute("wishlistCount", wishlistItems);
            model.addAttribute("totalPriceOfCart",cartService.getCartResponse(cart));
            session.setAttribute("totalPriceOfCart", cartService.getCartResponse(cart));
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

    @PostMapping("/save-address")
    public ResponseEntity<?> createNewAddress(@Valid @RequestBody Map<String, String> params,
                                              BindingResult result){
        try{
            if (result.hasErrors()){
                List<String> errors = result.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errors);
            }
            userService.saveAddress(params);
            return ResponseEntity.ok("Tao dia chi moi thanh cong");
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Tao that bai");
        }
    }

    @GetMapping("/get-address/{id}")
    public ResponseEntity<?> getAddress(@PathVariable Long id){
        try{
            Address address = addressService.findById(id).get();
            Map<String, String> params = new HashMap<>();
            params.put("city", address.getCity());
            params.put("district", address.getDistrict());
            params.put("street", address.getStreet());
            params.put("detailLocation", address.getDetailLocation());
            return ResponseEntity.ok(params);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PostMapping("/update-address")
    public ResponseEntity<?> updateAddress(@Valid @RequestBody Map<String, String> params, BindingResult result){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body("Error");
        }
        try{
            addressService.updateAddress(params);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }


    // Làm chức năng gì thì đặt endpoint là, /user/<name-feature>
    /*
        Ví dụ: Làm chức năng chi tiết sản phẩm (detail-cart)
        -> Đặt tên: @GetMapping("/user/detail/cart") nhưng ở trên có "/user" rồi
        -> còn lại là @GetMapping("/detail/cart")
    * */
}
