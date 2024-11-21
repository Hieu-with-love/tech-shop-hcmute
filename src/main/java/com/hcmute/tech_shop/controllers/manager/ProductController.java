package com.hcmute.tech_shop.controllers.manager;

import com.hcmute.tech_shop.dtos.requests.ProductRequest;
import com.hcmute.tech_shop.entities.Brand;
import com.hcmute.tech_shop.entities.Category;
import com.hcmute.tech_shop.entities.Product;
import com.hcmute.tech_shop.entities.ProductImage;
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
import java.util.*;

@Controller
@RequestMapping("/manager/products")
public class ProductController {
    @Autowired
    IProductService productService;
    @Autowired
    IBrandService brandService;
    @Autowired
    ICategoryService categoryService;
    @Autowired
    IProductImageService productImageService;

    @GetMapping("") // localhost:8080/manager/products
    public String index(Model model) {
        model.addAttribute("products", productService.findAll());
        return "manager/products/productlist";
    }

    @GetMapping("/computerDetail")
    public String computerDetail(Model model, @RequestParam Long id) {
        Optional<Product> product = productService.findById(id);
        List<ProductImage> images = productImageService.findByProductId(id);
        model.addAttribute("product", product);
        model.addAttribute("productImages", images);
        return "manager/products/computerDetail";
    }

    @GetMapping("/phoneDetail")
    public String phoneDetail(Model model, @RequestParam Long id) {
        Optional<Product> product = productService.findById(id);
        List<ProductImage> images = productImageService.findByProductId(id);
        model.addAttribute("product", product);
        model.addAttribute("productImages", images);
        return "manager/products/phoneDetail";
    }

    @GetMapping("/accessoryDetail")
    public String accessoryDetail(Model model, @RequestParam Long id) {
        Optional<Product> product = productService.findById(id);
        List<ProductImage> images = productImageService.findByProductId(id);
        model.addAttribute("product", product);
        model.addAttribute("productImages", images);
        return "manager/products/accessoryDetail";
    }

    @GetMapping("/add/computer")
    public String addComputer(Model model) {
        ProductRequest productDTO = new ProductRequest();
        model.addAttribute("productDto", productDTO);
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("categoryId", 1);
        model.addAttribute("categoryChoice", "computer");
        return "manager/products/addproduct";
    }

    @GetMapping("/add/phone")
    public String addPhone(Model model) {
        ProductRequest productDTO = new ProductRequest();
        model.addAttribute("productDto", productDTO);
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("categoryId", 2);
        model.addAttribute("categoryChoice", "phone");
        return "manager/products/addproduct";
    }

    @GetMapping("/add/accessory")
    public String addAccessory(Model model) {
        ProductRequest productDTO = new ProductRequest();
        model.addAttribute("productDto", productDTO);
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("categoryId", 3);
        model.addAttribute("categoryChoice", "accessory");
        return "manager/products/addproduct";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id) {
        Product product = productService.findById(id).get();
        model.addAttribute("productID", id);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
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

    @GetMapping("/images")
    public String images(Model model, @RequestParam Long id) {
        List<ProductImage> images = productImageService.findByProductId(id);
        model.addAttribute("id", id);
        model.addAttribute("productImages", images);
        return "manager/products/images";
    }

    @GetMapping("/images/delete")
    public String deleteImage(@RequestParam("image_id") Integer imageId) {
        productImageService.deleteById(imageId);
        return "redirect:/manager/products";
    }

    @PostMapping("/create")
    public String insert(@Valid @ModelAttribute("productDto") ProductRequest productDTO,
                         BindingResult bindingResult,
                         @RequestParam("files") MultipartFile file,
                         @RequestParam("categoryId") Long categoryId,
                         @RequestParam("categoryChoice") String categoryChoice,
                         Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("productDto", productDTO);
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("brands", brandService.findAll());
            model.addAttribute("categoryId", categoryId);
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
                         @RequestParam Long productID,
                         @Valid @ModelAttribute("product") ProductRequest productDTO,
                         BindingResult bindingResult,
                         @RequestParam("files") MultipartFile file,
                         @RequestParam("editComputer") String editComputer,
                         @RequestParam("editPhone") String editPhone,
                         @RequestParam("editAccessory") String editAccessory) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("productID", productID);
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("brands", brandService.findAll());
            model.addAttribute("product", productDTO);
            if (editComputer.equals("1")) {
                return "manager/products/editComputer";
            }
            if (editPhone.equals("1")) {
                return "manager/products/editPhone";
            }
            if (editAccessory.equals("1")) {
                return "manager/products/editAccessory";
            }
            return "manager/products/productlist";
        }
        if (file == null || file.isEmpty()) {
            Product product = productService.updateProduct(productID, productDTO);
            if (product == null) {
                // handle exception with alert, use js code
            }
            return "redirect:/manager/products";
        }

        Product product = productService.updateProduct(productID, productDTO, file);
        if (product == null) {
            // handle exception with alert, use js code
        }
        return "redirect:/manager/products";
    }

    @PostMapping("/images/create")
    public String insertImage(Model model,
                              @RequestParam("id") Long productid,
                              @RequestParam("files") MultipartFile[] files) throws IOException {
        Arrays.asList(files).stream().forEach(file -> {
            try {
                productImageService.createProductImages(productid, file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return "redirect:/manager/products";
    }
}
