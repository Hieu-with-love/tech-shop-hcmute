package com.hcmute.tech_shop.controllers.user;

import com.hcmute.tech_shop.dtos.requests.RatingRequest;
import com.hcmute.tech_shop.dtos.responses.ProductResponse;
import com.hcmute.tech_shop.entities.Product;
import com.hcmute.tech_shop.entities.ProductImage;
import com.hcmute.tech_shop.entities.Rating;
import com.hcmute.tech_shop.entities.User;
import com.hcmute.tech_shop.services.interfaces.IProductImageService;
import com.hcmute.tech_shop.services.interfaces.IProductService;
import com.hcmute.tech_shop.services.interfaces.IRatingService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RequestMapping("/user/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final IRatingService ratingService;
    private final IProductService productService;
    private final IProductImageService productImageService;

    @GetMapping("/product-detail/{id}")
    public String productDetail(Model model, @PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        ProductResponse productResponse = productService.getProductResponse(id);
        List<ProductImage> productImages = productImageService.findByProductId(id);
        List<Rating> ratings = ratingService.findByProductId(id);
        int ratingCount = ratingService.countRatingStar(id);
        int ratingUser = ratingService.countUser(id);

        model.addAttribute("product", product.get());
        model.addAttribute("productRes", productResponse);
        model.addAttribute("productImages", productImages);
        model.addAttribute("ratings", ratings);
        model.addAttribute("ratingCount", ratingCount);
        model.addAttribute("ratingUser", ratingUser);
        model.addAttribute("rating",new RatingRequest());

        return "user/single-product-3";
    }

    @PostMapping("/reviews")
    public String reviews(@Valid @ModelAttribute("rating") RatingRequest ratingRequest,
                          @RequestParam("productId") Long productId, HttpSession session,
                          RedirectAttributes redirectAttributes) {
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")){
            User user = (User) session.getAttribute("user");
            ratingRequest.setProductId(productId);
            ratingRequest.setUserId(user.getId());

            if(ratingService.checkOrderFirst(productId,user.getId())){
                if (!ratingService.insert(ratingRequest)){
                    String msg = "Not found user/product";
                    redirectAttributes.addFlashAttribute("msg", msg);
                }
            }
            else {
                String msg = "You need to buy first";
                redirectAttributes.addFlashAttribute("msg", msg);
            }
        }
        return "redirect:/user/products/product-detail/"+productId;
    }
}
