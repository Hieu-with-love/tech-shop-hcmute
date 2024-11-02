package com.hcmute.tech_shop.controllers.admin;

import com.hcmute.tech_shop.dtos.requests.CategoryRequest;
import com.hcmute.tech_shop.dtos.requests.ProductRequest;
import com.hcmute.tech_shop.entities.Brand;
import com.hcmute.tech_shop.entities.Category;
import com.hcmute.tech_shop.entities.Product;
import com.hcmute.tech_shop.services.interfaces.IBrandService;
import com.hcmute.tech_shop.services.interfaces.ICategoryService;
import com.hcmute.tech_shop.services.interfaces.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/admin/products")
public class ProductController {
    @Autowired
    IProductService productService;
    @Autowired
    IBrandService brandService;
    @Autowired
    ICategoryService categoryService;

    @GetMapping("") // localhost:8080/admin/products
    public String index(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin/products/productlist";
    }

    @GetMapping("/computerDetail")
    public String computerDetail(Model model, @RequestParam Long id) {
        Optional<Product> product = productService.findById(id);
        model.addAttribute("product", product);
        return "admin/products/computerDetail";
    }

    @GetMapping("/phoneDetail")
    public String phoneDetail(Model model, @RequestParam Long id) {
        Optional<Product> product = productService.findById(id);
        model.addAttribute("product", product);
        return "admin/products/phoneDetail";
    }

    @GetMapping("/accessoryDetail")
    public String accessoryDetail(Model model, @RequestParam Long id) {
        Optional<Product> product = productService.findById(id);
        model.addAttribute("product", product);
        return "admin/products/accessoryDetail";
    }

    @GetMapping("/add")
    public String add(Model model) {
        ProductRequest productDTO = new ProductRequest();
        //productDTO.setIsEdit(false);
        List<Category> categories = categoryService.findAll();
        List<Brand> brands = brandService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("product", productDTO);
        model.addAttribute("brands", brands);
        return "admin/products/addproduct";
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
            return "admin/products/editComputer";
        }
        if (product.getCategory().getName().equals("Phone")) {
            return "admin/products/editPhone";
        }
        if (product.getCategory().getName().equals("Accessory")) {
            return "admin/products/editAccessory";
        }
        return "admin/products";
    }

    @PostMapping("/create")
    public String insert(Model model,
                         @Valid @ModelAttribute("product") ProductRequest productDTO,
                         @RequestParam("files") MultipartFile file,
                         BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/products/addproduct";
        }

        if (productService.createProduct(productDTO, file)) {
            return "redirect:/admin/products";
        }
        return "admin/products/addproduct";
    }

    @PostMapping("/update")
    public String update(Model model,
                         @RequestParam Long id,
                         @Valid @ModelAttribute("product") ProductRequest productDTO,
                         @RequestParam("oldThumbnail") String oldThumbnail,
                         @RequestParam("files") MultipartFile file,
                         BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/products/productlist";
        }

        Product product = productService.updateProduct(id, productDTO, oldThumbnail, file);
        if (product == null) {
            // handle exception with alert, use js code
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/images")
    public String images(Model model, @RequestParam Long id) {
        Product product = productService.findById(id).get();
        model.addAttribute("product", product);
        return "admin/products/images";
    }
}
