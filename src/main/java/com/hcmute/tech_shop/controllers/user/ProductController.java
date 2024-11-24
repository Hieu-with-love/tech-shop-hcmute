package com.hcmute.tech_shop.controllers.user;

import com.hcmute.tech_shop.dtos.requests.CategoryRequest;
import com.hcmute.tech_shop.dtos.requests.ProductRequest;
import com.hcmute.tech_shop.entities.Category;
import com.hcmute.tech_shop.entities.Product;
import com.hcmute.tech_shop.entities.ProductImage;
import com.hcmute.tech_shop.entities.Rating;
import com.hcmute.tech_shop.services.interfaces.ICategoryService;
import com.hcmute.tech_shop.services.interfaces.IProductImageService;
import com.hcmute.tech_shop.services.interfaces.IProductService;
import com.hcmute.tech_shop.services.interfaces.IRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/{name}")
    public String getProductsByCategoryName(Model model, @PathVariable String name) {
        List<Category> categoryDTOList = categoryService.findAll();
        List<ProductRequest> productDTOList = productService.findByCategoryName(name);
        model.addAttribute("categories", categoryDTOList);
        model.addAttribute("categoryName", name);
        model.addAttribute("products", productDTOList);
        return "user/shop-sidebar";
    }

    @GetMapping("/single-product")
    public String singleProduct() {
        return "user/single-product-3";
    }

    @GetMapping("/product-detail/{id}")
    public String productDetail(Model model, @PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        List<ProductImage> productImages = productImageService.findByProductId(id);
        List<Rating> ratings = ratingService.findAll();
        int ratingCount = ratingService.countRatingStar(id);
        int ratingUser = ratingService.countUser(id);
        model.addAttribute("product", product.get());
        model.addAttribute("productImages", productImages);
        model.addAttribute("ratings", ratings);
        model.addAttribute("ratingCount", ratingCount);
        model.addAttribute("ratingUser", ratingUser);
        return "user/single-product-3";
    }
}
