package com.hcmute.tech_shop.controllers.user;

import com.hcmute.tech_shop.dtos.requests.RatingRequest;
import com.hcmute.tech_shop.dtos.responses.ProductResponse;
import com.hcmute.tech_shop.entities.*;
import com.hcmute.tech_shop.services.interfaces.IOrderService;
import com.hcmute.tech_shop.services.interfaces.IProductImageService;
import com.hcmute.tech_shop.services.interfaces.IProductService;
import com.hcmute.tech_shop.services.interfaces.IRatingService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/product-detail/{productId}/{orderId}")
    public String productDetail(Model model, @PathVariable Long productId, @PathVariable Long orderId) {
        Optional<Product> product = productService.findById(productId);
        ProductResponse productResponse = productService.getProductResponse(productId);
        List<ProductImage> productImages = productImageService.findByProductId(productId);
        List<Rating> ratings = ratingService.findByProductId(productId);
        int ratingCount = ratingService.countRatingStar(productId);
        int ratingUser = ratingService.countUser(productId);

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
