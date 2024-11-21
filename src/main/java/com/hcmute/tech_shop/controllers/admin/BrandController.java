package com.hcmute.tech_shop.controllers.admin;

import com.hcmute.tech_shop.dtos.requests.BrandRequest;
import com.hcmute.tech_shop.dtos.requests.ProductRequest;
import com.hcmute.tech_shop.entities.Brand;
import com.hcmute.tech_shop.entities.Category;
import com.hcmute.tech_shop.services.interfaces.IBrandService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/brands")
public class BrandController {

    @Autowired
    public IBrandService brandService;

    @GetMapping("")
    public String brands(Model model) {
        List<Brand> brands = brandService.findAll();
        model.addAttribute("brands", brands);
        return "admin/brands/list-brand";
    }

    @GetMapping("/add-brand")
    public String addBrand(Model model) {
        BrandRequest brandRequest = new BrandRequest();
        model.addAttribute("brand", brandRequest);
        return "admin/brands/add-brand";
    }

    @PostMapping("/insert")
    public String insertBrand(@Valid@ModelAttribute("brand") BrandRequest brandRequest, @RequestParam("files")MultipartFile file, BindingResult bindingResult, Model model) throws IOException {
        String msg = "";
        if (bindingResult.hasErrors()) {
            msg = bindingResult.getFieldError().getDefaultMessage();
            model.addAttribute("msg", msg);
            return "admin/brands/list-brand";
        }
        if (!brandRequest.getName().matches("^[a-zA-Z].*")) {
            msg = "Brand Name must start with a letter";
            model.addAttribute("error", msg);
            model.addAttribute("brand", brandRequest);
            return "admin/brands/add-brand";
        }
        if (!brandRequest.getName().matches("^[a-zA-Z0-9\\s]+$")){
            msg = "Name is not valid";
            model.addAttribute("error", msg);
            return "admin/brands/add-brand";
        }
        if(brandService.findByName(brandRequest.getName()) != null){
            msg = "Name already exists";
            model.addAttribute("error", msg);
            return "admin/brands/add-brand";
        }
        if (brandService.addBrand(brandRequest,file)){
            return "redirect:/admin/brands";
        }
        else {
            msg = "Something went wrong";
            model.addAttribute("error", msg);
        }
        return "admin/brands/add-brand";
    }

    @GetMapping("/edit-brand/{id}")
    public String editBrand(@PathVariable("id") Long id, Model model) {
        Brand brand = brandService.findById(id).get();
        model.addAttribute("brand", brand);
        return "admin/brands/edit-brand";
    }

    @PostMapping("/edit-brand/{id}")
    public String editBrand(@Valid @ModelAttribute("brand") BrandRequest brandRequest,
                            Model model, @PathVariable("id") Long id,
                            @RequestParam("oldImg") String oldImg,
                            @RequestParam("files") MultipartFile file,
                            BindingResult bindingResult) {
        String msg = "";

        if (bindingResult.hasErrors()) {
            msg = bindingResult.getFieldError().getDefaultMessage();
            model.addAttribute("msg", msg);
            return "admin/brands/list-brand";
        }
        if (!brandRequest.getName().matches("^[a-zA-Z].*")) {
            msg = "Brand Name must start with a letter";
            model.addAttribute("error", msg);
            model.addAttribute("brand", brandRequest);
            return "admin/brands/add-brand";
        }
        if (!brandRequest.getName().matches("^[a-zA-Z0-9\\s]+$")){
            msg = "Name is not valid";
            model.addAttribute("msg", msg);
            return "admin/brands/add-brand";
        }
        Brand brand = brandService.findByName(brandRequest.getName());
        Brand brandOld = brandService.findById(id).get();
        if(brand != null && !brand.getId().equals(id) ) {
            msg = "Brand already exists";
            model.addAttribute("error", msg);
            model.addAttribute("brand", brandOld);
            return "admin/brands/edit-brand";
        }
        if (file == null || file.isEmpty()) {
            if(brandService.updateBrand(brandRequest,id,oldImg)){
                return "redirect:/admin/brands";
            }
        }
        else if(file != null && !file.isEmpty()) {
            if(brandService.updateBrand(brandRequest,id,oldImg, file)) {
                return "redirect:/admin/brands";
            }
        }
        else {
            msg = "Something went wrong";
            model.addAttribute("msg", msg);
            model.addAttribute("category", brandOld);
        }
        return "admin/brands/edit-brand";
    }

    @GetMapping("/delete-brand/{id}")
    public ResponseEntity<Map<String, String>> deleteBrand(@PathVariable(value = "id") Long id) {
        Map<String, String> response = new HashMap<>();
        if (brandService.deleteBrand(id)) {
            response.put("status", "success");
            response.put("message", "Brand deleted successfully.");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Failed to delete the brand.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
