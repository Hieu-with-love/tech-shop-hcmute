package com.hcmute.tech_shop.controllers.admin;

import com.hcmute.tech_shop.dtos.requests.CategoryRequest;
import com.hcmute.tech_shop.entities.Category;
import com.hcmute.tech_shop.services.interfaces.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    ICategoryService categoryService;

    @GetMapping("")
    public String index(Model model) {
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categories", categoryList);
        return "admin/categories/list-category";
    }

    @GetMapping("/add-category")
    public String addCategory(Model model) {
        CategoryRequest categoryRequest = new CategoryRequest();
        model.addAttribute("category", categoryRequest);
        return "admin/categories/add-category";
    }

    @PostMapping("/insert")
    public String addCategory(@Valid @ModelAttribute("category") CategoryRequest categoryRequest, BindingResult bindingResult, Model model) {
        String msg = "";
        if (bindingResult.hasErrors()) {
            msg = bindingResult.getFieldError().getDefaultMessage();
            model.addAttribute("error", msg);
            return "admin/categories/list-category";
        }
        if(!categoryRequest.getName().matches("^[a-zA-Z\\s]+$")){
            msg = "Category Name cannot contain numbers or special characters";
            model.addAttribute("error", msg);
            return "admin/categories/add-category";
        }
        if(categoryService.findByCategoryName(categoryRequest.getName()) != null){
            msg = "Category already exists";
            model.addAttribute("error", msg);
            return "admin/categories/add-category";
        }
        if(categoryService.addCategory(categoryRequest)) {
            return "redirect:/admin/categories";
        }
        else {
            msg = "Category not added";
            model.addAttribute("error", msg);
        }
        return "admin/categories/add-category";
    }

    @GetMapping("/edit-category/{id}")
    public String editCategory(Model model, @PathVariable(value = "id") Long id) {
        Category category = categoryService.findById(id).get();
        model.addAttribute("category", category);
        return "admin/categories/edit-category";
    }

    @PostMapping("/edit-category/{id}")
    public String editCategory(Model model, @ModelAttribute("category") CategoryRequest categoryRequest, @PathVariable(value = "id") Long id, Model model1) {
        String msg = "";
        if(!categoryRequest.getName().matches("^[a-zA-Z\\s]+$")){
            msg = "Category Name cannot contain numbers or special characters";
            model.addAttribute("error", msg);
            return "admin/categories/edit-category";
        }
        Category category = categoryService.findByCategoryName(categoryRequest.getName());
        Category categoryOld = categoryService.findById(id).get();
        if(category != null && !category.getId().equals(id) ) {
            msg = "Category already exists";
            model.addAttribute("error", msg);
            model.addAttribute("category", categoryOld);
            return "admin/categories/edit-category";
        }
        if (categoryService.updateCategory(categoryRequest,id)) {
            return "redirect:/admin/categories";
        }
        else {
            msg = "Category already exists";
            model.addAttribute("error", msg);
            model.addAttribute("category", categoryOld);
            return "admin/categories/edit-category";
        }
    }

    @GetMapping("/delete-category/{id}")
    public ResponseEntity<Map<String, String>> deleteCategory(@PathVariable(value = "id") Long id) {
        Map<String, String> response = new HashMap<>();

        if (categoryService.deleteCategory(id)) {
            response.put("status", "success");
            response.put("message", "Category deleted successfully.");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Failed to delete the category.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
