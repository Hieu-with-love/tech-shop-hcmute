package com.hcmute.tech_shop.controllers.user;

import com.hcmute.tech_shop.entities.Product;
import com.hcmute.tech_shop.services.interfaces.ICategoryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user/cart")
public class CartController {

    private ICategoryService categoryService;

    @GetMapping("")
    public String cart(HttpSession session) {
        List<Product> cartList = (List<Product>) session.getAttribute("cart");
        if(cartList == null) {
            cartList = new ArrayList<Product>();
            session.setAttribute("cart", cartList);
        }
        return "user/cart";
    }

//    @PostMapping("/addToCart")
//    public String addToCart(HttpSession session, Model model, @RequestParam("id") Long id) {
//        String username = (String) session.getAttribute("username");
//        if (username != null) {
//
//        }
//    }
}
