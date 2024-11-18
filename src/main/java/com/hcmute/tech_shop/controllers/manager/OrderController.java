package com.hcmute.tech_shop.controllers.manager;

import com.hcmute.tech_shop.dtos.requests.ProductRequest;
import com.hcmute.tech_shop.entities.*;
import com.hcmute.tech_shop.services.interfaces.IBrandService;
import com.hcmute.tech_shop.services.interfaces.ICategoryService;
import com.hcmute.tech_shop.services.interfaces.IProductImageService;
import com.hcmute.tech_shop.services.interfaces.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller("controllerOfManager")
@RequestMapping("/manager/orders")
public class OrderController {
    @Autowired
    IProductService productService;
    @Autowired
    IBrandService brandService;
    @Autowired
    ICategoryService categoryService;
    @Autowired
    IProductImageService productImageService;

    @GetMapping("") // localhost:8080/manager/orders
    public String index(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "manager/orders/orderlist";
    }

    @GetMapping("/detail")
    public String computerDetail(Model model, @RequestParam Long id) {
        Optional<Product> product = productService.findById(id);
        List<ProductImage> images = productImageService.findByProductId(id);
        model.addAttribute("product", product);
        model.addAttribute("productImages", images);
        return "manager/orders/orderDetail";
    }

    @GetMapping("/add")
    public String add(Model model) {
        ProductRequest productDTO = new ProductRequest();
//        model.addAttribute("product", productDTO);
//        model.addAttribute("brands", brandService.findAll());
//        model.addAttribute("categoryId", 1);
//        model.addAttribute("categoryChoice", "computer");
        return "manager/orders/addorder";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id) {
        Product product = productService.findById(id).get();
        List<Category> categories = categoryService.findAll();
        List<Brand> brands = brandService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        model.addAttribute("product", product);
        if (product.getCategory().getName().equals("Computer")) {
            return "manager/products/editComputer";
        }
        if (product.getCategory().getName().equals("Phone")) {
            return "manager/products/editPhone";
        }
        if (product.getCategory().getName().equals("Accessory")) {
            return "manager/products/editAccessory";
        }
        return "redirect:manager/products";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam Long id) {
        productService.deleteProduct(id);
        return "redirect:/manager/products";
    }

    @PostMapping("/create")
    public String insert(@Valid @ModelAttribute("product") ProductRequest productDTO,
                         @RequestParam("files") MultipartFile file,
                         @RequestParam("categoryId") Long categoryId,
                         @RequestParam("categoryChoice") String categoryChoice,
                         Model model,
                         BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", productDTO);
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("brands", brandService.findAll());
            model.addAttribute("categoryChoice", categoryChoice);
            return "manager/products/addproduct";
        }
        productDTO.setCategoryId(categoryId);
        if (productService.createProduct(productDTO, file)) {
            return "redirect:/manager/products";
        }
        return "manager/products/addproduct";
    }

    @PostMapping("/update")
    public String update(Model model,
                         @RequestParam Long id,
                         @Valid @ModelAttribute("product") ProductRequest productDTO,
                         @RequestParam("files") MultipartFile file,
                         BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return "manager/products/productlist";
        }
        if (file == null || file.isEmpty()) {
            Product product = productService.updateProduct(id, productDTO);
            if (product == null) {
                // handle exception with alert, use js code
            }
            return "redirect:/manager/products";
        }

        Product product = productService.updateProduct(id, productDTO, file);
        if (product == null) {
            // handle exception with alert, use js code
        }
        return "redirect:/manager/products";
    }
}
