package com.hcmute.tech_shop.controllers.user;

import com.hcmute.tech_shop.dtos.requests.CartDetailRequest;
import com.hcmute.tech_shop.dtos.requests.UserRequest;
import com.hcmute.tech_shop.dtos.responses.CartDetailResponse;
import com.hcmute.tech_shop.dtos.responses.CartResponse;
import com.hcmute.tech_shop.entities.*;
import com.hcmute.tech_shop.services.interfaces.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user/cart")
public class CartController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ICartDetailService cartDetailServiceImpl;

    @Autowired
    private IProductService productServiceImpl;

    @Autowired
    private WishlistItemService wishlistItemServiceImpl;

    @Autowired
    private WishlistService wishlistServiceImpl;

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

    @GetMapping("")
    public String cart(Model model, HttpSession session) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<CartDetailResponse> cartDetailList = new ArrayList<>();
        List<CartDetailResponse> cartDetailListFull = new ArrayList<>();
        int numberProductInCart = 0;

        Cart cart = new Cart();
        if(!username.equals("anonymousUser")) {
            UserRequest userRequest = getUser();
            cart = cartService.findByCustomerId(userRequest.getId());
            Wishlist wishlist = wishlistServiceImpl.getWishlistByUserId(userRequest.getId());
            int wishlistItems = wishlistItemServiceImpl.getItemsCount(wishlist.getId());
            if(cart == null) {
                cart = cartService.createCart(new Cart(null,BigDecimal.ZERO,userRequest.getId(),null));
            }
            cartDetailList = cartDetailServiceImpl.getAllItems(cartDetailServiceImpl.findAllByCart_Id(cart.getId()));
            numberProductInCart = cartDetailList.size();
            cartDetailListFull = cartDetailList;
            if(cartDetailList.size() > 3) {
                cartDetailList = cartDetailList.subList(0, 3);
            }
            session.setAttribute("wishlistId", wishlist.getId());
            session.setAttribute("wishlistCount", wishlistItems);
            model.addAttribute("isEmptyCart", cartDetailList.isEmpty());
        }

        User user = userService.getUserByUsername(username);
        session.setAttribute("user", user);
        model.addAttribute("cart",cart);
        model.addAttribute("cartDetailListFull", cartDetailListFull);
        model.addAttribute("cartDetailList", cartDetailList);
        model.addAttribute("numberProductInCart", numberProductInCart);
        model.addAttribute("totalPriceOfCart",cartService.getCartResponse(cart));

        session.setAttribute("cart", cart);
        session.setAttribute("cartDetailListFull", cartDetailListFull);
        session.setAttribute("numberProductInCart", numberProductInCart);
        session.setAttribute("totalPriceOfCart",cartService.getCartResponse(cart));

        return "user/cart";
    }

    @PostMapping("/cart-add")
    public String addToCart(Model model, @Valid @RequestParam("productId") Long productId,@RequestParam("quantity") int quantity, HttpSession session, RedirectAttributes redirectAttributes) {
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart == null) {
            cart = cartService.createCart(new Cart(null,BigDecimal.ZERO,cart.getUserId(),null));
        }

        Product product = productServiceImpl.findById(productId).orElse(null);
        CartDetail cartDetail = cartDetailServiceImpl.findByCart_IdAndProductId(cart.getId(), productId);
        CartDetailRequest cartDetailRequest;
        BigDecimal price;
        BigDecimal limit = new BigDecimal("10000000000");

        if(cartDetail != null) {
            price = product.getPrice().multiply(new BigDecimal(cartDetail.getQuantity()+quantity));
            cartDetailRequest = new CartDetailRequest(quantity + cartDetail.getQuantity(), price, cart, product);

            if (cartDetailRequest.getQuantity() > product.getStockQuantity()){
                String error = "Could not add quantity";
                redirectAttributes.addFlashAttribute("error", error);
            }
            else if (price.compareTo(limit) > 0){
                String error = "The cart value in your cart has reached the limit.";
                redirectAttributes.addFlashAttribute("error", error);
            }
            if (!cartDetailServiceImpl.update(cartDetailRequest)) {
                String error = "Could not update cart detail";
                redirectAttributes.addFlashAttribute("error", error);
            }
        }
        else
        {
            price = product.getPrice().multiply(new BigDecimal(quantity));
            cartDetailRequest = new CartDetailRequest(quantity,price, cart, product);

            if(price.compareTo(limit) > 0){
                String error = "The cart value in your cart has reached the limit.";
                redirectAttributes.addFlashAttribute("error", error);
            }
            else if (!cartDetailServiceImpl.create(cartDetailRequest)) {
                String error = "Could not create cart detail";
                redirectAttributes.addFlashAttribute("error", error);
            }
        }
        return "redirect:/user/home";
    }

    @GetMapping("/inc-cart")
    public String incrementCart(@Valid @RequestParam("productId") Long productId, HttpSession session, RedirectAttributes redirectAttributes) {
        Cart cart = (Cart) session.getAttribute("cart");
        cart = cartService.findById(cart.getId());

        Product product = productServiceImpl.findById(productId).get();
        CartDetail cartDetail = cartDetailServiceImpl.findByCart_IdAndProductId(cart.getId(), product.getId());
        CartDetailRequest cartDetailRequest;
        BigDecimal price,limit = new BigDecimal("10000000000");

        if(cartDetail != null) {
            price = product.getPrice().multiply(new BigDecimal(cartDetail.getQuantity()+1));
            cartDetailRequest = new CartDetailRequest(cartDetail.getQuantity() + 1, price, cart, product);
            if (cartDetailRequest.getQuantity() > product.getStockQuantity()){
                String error = "Not enough stock";
                redirectAttributes.addFlashAttribute("error", error);
            }
            else if(price.compareTo(limit) > 0){
                String error = "The cart value in your cart has reached the limit.";
                redirectAttributes.addFlashAttribute("error", error);
            }
            else if (!cartDetailServiceImpl.update(cartDetailRequest)) {
                String error = "Could not update cart detail";
                redirectAttributes.addFlashAttribute("error", error);
            }
        }
        return "redirect:/user/cart";
    }

    @GetMapping("/dec-cart")
    public String decrementCart(@Valid @RequestParam("productId") Long productId, HttpSession session, RedirectAttributes redirectAttributes) {
        Cart cart = (Cart) session.getAttribute("cart");
        cart = cartService.findById(cart.getId());

        Product product = productServiceImpl.findById(productId).get();
        CartDetail cartDetail = cartDetailServiceImpl.findByCart_IdAndProductId(cart.getId(), product.getId());
        CartDetailRequest cartDetailRequest;
        BigDecimal price;

        if(cartDetail != null) {
            if(cartDetail.getQuantity() > 1) {
                price = product.getPrice().multiply(new BigDecimal(cartDetail.getQuantity()-1));
                cartDetailRequest = new CartDetailRequest(cartDetail.getQuantity() - 1, price, cart, product);
                if (!cartDetailServiceImpl.update(cartDetailRequest)) {
                    String error = "Could not update cart detail";
                    redirectAttributes.addFlashAttribute("error", error);
                }
            }
            else {
                String error = "Quantity must be greater than 1";
                redirectAttributes.addFlashAttribute("error", error);
            }
        }
        return "redirect:/user/cart";
    }

    @GetMapping("/delete-all")
    public String deleteAllCart(HttpSession session, RedirectAttributes redirectAttributes) {
        Cart cart = (Cart) session.getAttribute("cart");
        cart = cartService.findById(cart.getId());

        if(cart != null) {
            if(!cartDetailServiceImpl.deleteAll(cart.getId())){
                String error = "Could not delete cart detail";
                redirectAttributes.addFlashAttribute("error", error);
            }
        }

        return "redirect:/user/cart";
    }

    @GetMapping("/delete/{id}")
    public String delete(@Valid @PathVariable("id") Long producId, HttpSession session, RedirectAttributes redirectAttributes) {
        Cart cart = (Cart) session.getAttribute("cart");
        cart = cartService.findById(cart.getId());

        Product product = productServiceImpl.findById(producId).get();
        CartDetail cartDetail = cartDetailServiceImpl.findByCart_IdAndProductId(cart.getId(), product.getId());
        if(cartDetail != null) {
            if(!cartDetailServiceImpl.delete(cartDetail)) {
                String error = "Could not delete cart detail";
                redirectAttributes.addFlashAttribute("error", error);
            }
        }

        return "redirect:/user/cart";
    }

    @PostMapping("/cart-add-wishlist")
    public String addToCartFromWishList(Model model, @Valid @RequestParam("productId") Long productId,@RequestParam("quantity") int quantity, HttpSession session, RedirectAttributes redirectAttributes) {
        Cart cart = (Cart) session.getAttribute("cart");
        cart = cartService.findById(cart.getId());

        Long wishlistId = (Long) session.getAttribute("wishlistId");
        if(cart == null) {
            cart = cartService.createCart(new Cart(null,BigDecimal.ZERO,cart.getUserId(),null));
        }

        Product product = productServiceImpl.findById(productId).orElse(null);
        CartDetail cartDetail = cartDetailServiceImpl.findByCart_IdAndProductId(cart.getId(), productId);
        CartDetailRequest cartDetailRequest;
        BigDecimal price;
        BigDecimal limit = new BigDecimal("10000000000");

        if(cartDetail != null) {
            price = product.getPrice().multiply(new BigDecimal(cartDetail.getQuantity()+quantity));
            cartDetailRequest = new CartDetailRequest(quantity + cartDetail.getQuantity(), price, cart, product);

            if (cartDetailRequest.getQuantity() > product.getStockQuantity()){
                String error = "Not enough stock";
                redirectAttributes.addFlashAttribute("error", error);
            }
            else if (price.compareTo(limit) > 0){
                String error = "The cart value in your cart has reached the limit.";
                redirectAttributes.addFlashAttribute("error", error);
            }
            else if (!cartDetailServiceImpl.update(cartDetailRequest)) {
                String error = "Could not update cart detail";
                redirectAttributes.addFlashAttribute("error", error);
            }
            else {
                wishlistItemServiceImpl.removeItemFromWishlist(wishlistId, productId);
            }
        }
        else
        {
            price = product.getPrice().multiply(new BigDecimal(quantity));
            cartDetailRequest = new CartDetailRequest(quantity,price, cart, product);

            if(price.compareTo(limit) > 0){
                String error = "The cart value in your cart has reached the limit.";
                redirectAttributes.addFlashAttribute("error", error);
            }
            else if (!cartDetailServiceImpl.create(cartDetailRequest)) {
                String error = "Could not create cart detail";
                redirectAttributes.addFlashAttribute("error", error);
            }
            else {
                wishlistItemServiceImpl.removeItemFromWishlist(wishlistId, productId);
            }
        }


        return "redirect:/user/cart";
    }
}
