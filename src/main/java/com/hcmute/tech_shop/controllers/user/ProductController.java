package com.hcmute.tech_shop.controllers.user;

import com.hcmute.tech_shop.dtos.requests.ProductRequest;

import com.hcmute.tech_shop.dtos.requests.RatingRequest;
import com.hcmute.tech_shop.entities.*;
import com.hcmute.tech_shop.services.interfaces.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import com.hcmute.tech_shop.dtos.responses.ProductResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller(value = "UserProductController")
@RequestMapping("/user/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;
    private final IProductImageService productImageService;
    private final IRatingService ratingService;
    private final ICategoryService categoryService;

    private final IOrderDetailService orderDetailService;

    private final IBrandService brandService;


    @GetMapping("/{name}")
    public String getProductsByCategoryName(Model model, @PathVariable String name) {
        List<Category> categoryDTOList = categoryService.findAll();
        List<Brand> brands = brandService.findAll();
        List<ProductRequest> productDTOList = productService.findByCategoryName(name);

        model.addAttribute("brands", brands);

        model.addAttribute("categories", categoryDTOList);
        model.addAttribute("categoryName", name);
        model.addAttribute("products", productDTOList);
        model.addAttribute("rating",new RatingRequest());
        return "user/shop-sidebar";
    }

    @GetMapping("")
    public String getProducts(@RequestParam Map<String, Object> params,
                              @RequestParam(defaultValue = "0") int pageNumber,
                              @RequestParam(defaultValue = "12") int pageSize,
                              @RequestParam(defaultValue = "asc") String sortOrder,
                              @RequestParam(defaultValue = "price") String sortBy,
                              Model model) {

        Sort sort = sortOrder.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<ProductResponse> productPage = productService.filterProducts(params, pageable);

        int totalItems = (int) productPage.getTotalElements();
        int totalPages = productPage.getTotalPages();
        String showingInfo = String.format("Showing %d-%d of %d results", pageNumber * pageSize + 1,
                Math.min((pageNumber + 1) * pageSize, totalItems), totalItems);

        List<Category> categoryDTOList = categoryService.findAll();
        List<Brand> brands = brandService.findAll();

        String queryParams = params.entrySet().stream()
                .filter(entry -> !"pageNumber".equals(entry.getKey()) && !"pageSize".equals(entry.getKey()) && !"sortBy".equals(entry.getKey()) && !"sortOrder".equals(entry.getKey()))
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
        String baseUrl = "/user/products" + (queryParams.isEmpty() ? "?" : "?" + queryParams + "&");

        model.addAttribute("brands", brands);
        model.addAttribute("categories", categoryDTOList);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("showingInfo", showingInfo);
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("pageSize", pageSize);

        return "user/shop-sidebar";
    }


    @GetMapping("/single-product")
    public String singleProduct() {
        return "user/single-product-3";
    }


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

    @GetMapping("/quick-view")
    @ResponseBody
    public ResponseEntity<Map<String, String>> quickView(@RequestParam("id") Long productId) {
        // Lấy thông tin sản phẩm từ cơ sở dữ liệu
        ProductResponse product = productService.getProductResponse(productId);
        int ratings= ratingService.countUser(productId);
        String ratingString = ratings > 0 ? String.valueOf(ratings) : "Chưa có đánh giá";
        // Tạo Map để trả về dữ liệu
        Map<String, String> response = new HashMap<>();
        response.put("name", product.getName());
        response.put("price", String.valueOf(product.getPrice())); // Chuyển giá trị số sang chuỗi
        response.put("oldPrice", String.valueOf(product.getOldPrice()));
        response.put("thumbnail", product.getThumbnail());
        response.put("stockQuantity", String.valueOf(product.getStockQuantity()));
        response.put("isUrlImage", String.valueOf(product.isUrlImage()));
        response.put("ratings", ratingString);
        return ResponseEntity.ok(response);
    }
}
