package com.hcmute.tech_shop.controllers.admin;

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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller("controllerOfAdmin")
@RequestMapping("/admin/products")
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
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin/products/productlist";
    }

    @GetMapping("/computerDetail")
    public String computerDetail(Model model, @RequestParam Long id) {
        Optional<Product> product = productService.findById(id);
        List<ProductImage> images = productImageService.findByProductId(id);
        model.addAttribute("product", product);
        model.addAttribute("productImages", images);
        return "admin/products/computerDetail";
    }

    @GetMapping("/phoneDetail")
    public String phoneDetail(Model model, @RequestParam Long id) {
        Optional<Product> product = productService.findById(id);
        List<ProductImage> images = productImageService.findByProductId(id);
        model.addAttribute("product", product);
        model.addAttribute("productImages", images);
        return "admin/products/phoneDetail";
    }

    @GetMapping("/accessoryDetail")
    public String accessoryDetail(Model model, @RequestParam Long id) {
        Optional<Product> product = productService.findById(id);
        List<ProductImage> images = productImageService.findByProductId(id);
        model.addAttribute("product", product);
        model.addAttribute("productImages", images);
        return "admin/products/accessoryDetail";
    }

    @GetMapping("/add/computer")
    public String addComputer(Model model) {
        ProductRequest productDTO = new ProductRequest();
        model.addAttribute("product", productDTO);
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("categoryId", 1);
        model.addAttribute("categoryChoice", "computer");
        return "admin/products/addproduct";
    }

    @GetMapping("/add/phone")
    public String addPhone(Model model) {
        ProductRequest productDTO = new ProductRequest();
        model.addAttribute("product", productDTO);
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("categoryId", 2);
        model.addAttribute("categoryChoice", "phone");
        return "admin/products/addproduct";
    }

    @GetMapping("/add/accessory")
    public String addAccessory(Model model) {
        ProductRequest productDTO = new ProductRequest();
        model.addAttribute("product", productDTO);
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("categoryId", 3);
        model.addAttribute("categoryChoice", "accessory");
        return "admin/products/addproduct";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id) {
        Product product = productService.findById(id).get();
        model.addAttribute("productID", id);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
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
        return "redirect:admin/products";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/images")
    public String images(Model model, @RequestParam Long id) {
        List<ProductImage> images = productImageService.findByProductId(id);
        model.addAttribute("id", id);
        model.addAttribute("productImages", images);
        return "admin/products/images";
    }

    @GetMapping("/images/delete")
    public String deleteImage(@RequestParam("image_id") Integer imageId) {
        productImageService.deleteById(imageId);
        return "redirect:/admin/products";
    }

    @PostMapping("/create")
    public String insert(@Valid @ModelAttribute("product") ProductRequest productDTO,
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
            return "admin/products/addproduct";
        }
        productDTO.setCategoryId(categoryId);
        if (productService.createProduct(productDTO, file)) {
            return "redirect:/admin/products";
        }
        return "admin/products/addproduct";
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
                return "admin/products/editComputer";
            }
            if (editPhone.equals("1")) {
                return "admin/products/editPhone";
            }
            if (editAccessory.equals("1")) {
                return "admin/products/editAccessory";
            }
            return "admin/products/productlist";
        }
        if (file == null || file.isEmpty()) {
            Product product = productService.updateProduct(productID, productDTO);
            if (product == null) {
                // handle exception with alert, use js code
            }
            return "redirect:/admin/products";
        }

        Product product = productService.updateProduct(productID, productDTO, file);
        if (product == null) {
            // handle exception with alert, use js code
        }
        return "redirect:/admin/products";
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
        return "redirect:/admin/products";
    }
}
