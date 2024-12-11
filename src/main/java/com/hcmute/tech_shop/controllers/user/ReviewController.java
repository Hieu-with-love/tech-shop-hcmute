package com.hcmute.tech_shop.controllers.user;

import com.hcmute.tech_shop.dtos.requests.RatingRequest;
import com.hcmute.tech_shop.dtos.requests.UserRequest;
import com.hcmute.tech_shop.dtos.responses.CartDetailResponse;
import com.hcmute.tech_shop.dtos.responses.ProductResponse;
import com.hcmute.tech_shop.entities.*;
import com.hcmute.tech_shop.services.interfaces.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final IRatingService ratingService;
    private final IProductService productService;
    private final IProductImageService productImageService;
    private final IOrderService orderService;
    private final UserService userService;
    private final CartService cartService;
    private final ICartDetailService cartDetailService;

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

    @GetMapping("/product-detail/{productId}/{orderId}")
    public String productDetail(Model model, @PathVariable Long productId, @PathVariable Long orderId) {
        Optional<Product> product = productService.findById(productId);
        ProductResponse productResponse = productService.getProductResponse(productId);
        List<ProductImage> productImages = productImageService.findByProductId(productId);
        List<Rating> ratings = ratingService.findByProductId(productId);
        int ratingCount = ratingService.countRatingStar(productId);
        int ratingUser = ratingService.countUser(productId);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<CartDetailResponse> cartDetailList = new ArrayList<>();

        int numberProductInCart = 0;

        Cart cart = new Cart();
        if(!username.equals("anonymousUser")) {
            User user = userService.getUserByUsername(username);

            UserRequest userRequest = getUser();
            cart = cartService.findByCustomerId(userRequest.getId());
            if(cart == null) {
                cart = cartService.createCart(new Cart(null, BigDecimal.ZERO,userRequest.getId(),null));
            }
            cartDetailList = cartDetailService.getAllItems(cartDetailService.findAllByCart_Id(cart.getId()));
            numberProductInCart = cartDetailList.size();
            if(cartDetailList.size() > 3) {
                cartDetailList = cartDetailList.subList(0, 3);
            }
            model.addAttribute("totalPriceOfCart",cartService.getCartResponse(cart));
        }

        model.addAttribute("cart", cart);
        model.addAttribute("cartDetailList", cartDetailList);
        model.addAttribute("numberProductInCart", numberProductInCart);

        model.addAttribute("product", product.get());
        model.addAttribute("productRes", productResponse);
        model.addAttribute("productImages", productImages);
        model.addAttribute("ratings", ratings);
        model.addAttribute("ratingCount", ratingCount);
        model.addAttribute("ratingUser", ratingUser);
        model.addAttribute("rating",new RatingRequest());
        model.addAttribute("orderId", orderId);

        return "user/review";
    }

    @PostMapping("/{productId}/{orderId}")
    public String reviews(@Valid @ModelAttribute("rating") RatingRequest ratingRequest,
                          @PathVariable Long productId, HttpSession session,
                          @PathVariable Long orderId,
                          RedirectAttributes redirectAttributes) {
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")){
            User user = (User) session.getAttribute("user");
            ratingRequest.setProductId(productId);
            ratingRequest.setUserId(user.getId());
            ratingRequest.setOrderId(orderId);

            if(ratingService.checkOrderFirst(productId,user.getId())){
                if (!ratingService.insert(ratingRequest)){
                    String msg = "Not found user/product";
                    redirectAttributes.addFlashAttribute("msg", msg);
                    return "redirect:/user/reviews/"+productId+"/"+orderId;
                }
            }
            else {
                String msg = "You need to buy first";
                redirectAttributes.addFlashAttribute("msg", msg);
                return "redirect:/user/reviews/"+productId+"/"+orderId;
            }
        }
        return "redirect:/user/orders/detail?id="+orderId;
    }
}
