package com.hcmute.tech_shop.controllers.user;

import com.hcmute.tech_shop.dtos.requests.CategoryRequest;
import com.hcmute.tech_shop.dtos.requests.ProductRequest;
import com.hcmute.tech_shop.dtos.requests.RatingRequest;
import com.hcmute.tech_shop.entities.*;
import com.hcmute.tech_shop.services.interfaces.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller(value = "UserProductController")
@RequestMapping("/user/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;
    private final IProductImageService productImageService;
    private final IRatingService ratingService;
    private final ICategoryService categoryService;
    private final IOrderDetailService orderDetailService;

    @GetMapping("/{name}")
    public String getProductsByCategoryName(Model model, @PathVariable String name) {
        List<Category> categoryDTOList = categoryService.findAll();
        List<ProductRequest> productDTOList = productService.findByCategoryName(name);

        model.addAttribute("categories", categoryDTOList);
        model.addAttribute("categoryName", name);
        model.addAttribute("products", productDTOList);
        model.addAttribute("rating",new RatingRequest());
        return "user/shop-sidebar";
    }

    @GetMapping("/product-detail/{id}")
    public String productDetail(Model model, @PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        List<ProductImage> productImages = productImageService.findByProductId(id);
        List<Rating> ratings = ratingService.findByProductId(id);
        int ratingCount = ratingService.countRatingStar(id);
        int ratingUser = ratingService.countUser(id);

        model.addAttribute("product", product.get());
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
