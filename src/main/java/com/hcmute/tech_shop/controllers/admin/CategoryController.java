package com.hcmute.tech_shop.controllers.admin;

import com.hcmute.tech_shop.dtos.requests.CategoryRequest;
import com.hcmute.tech_shop.services.interfaces.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    ICategoryService categoryService;

    @GetMapping("")
    public String index(Model model) {
        List<CategoryRequest> categoryRequestList = categoryService.findAll();
        model.addAttribute("categories", categoryRequestList);
        return "admin/categories/list-category";
    }

    @GetMapping("/add-category")
    public String addCategory(Model model) {
        CategoryRequest categoryRequest = new CategoryRequest();
        model.addAttribute("category", categoryRequest);
        return "admin/categories/add-category";
    }

    @PostMapping("/insert")
    public String addCategory(@Valid @ModelAttribute("category") CategoryRequest categoryRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/categories/list-category";
        }
        if(categoryService.addCategory(categoryRequest)) {
            return "redirect:/admin/categories";
        }
        return "admin/categories/add-category";
    }

    @GetMapping("/edit-category/{id}")
    public String editCategory(Model model, @PathVariable(value = "id") Long id) {
        CategoryRequest categoryRequest = categoryService.findById(id).get();
        model.addAttribute("category", categoryRequest);
        return "admin/categories/edit-category";
    }

    @PostMapping("/edit-category")
    public String editCategory(Model model, @ModelAttribute("category") CategoryRequest categoryRequest) {
        if (categoryService.updateCategory(categoryRequest)) {
            return "redirect:/admin/categories";
        }
        else {
            model.addAttribute("category", categoryRequest);
            return "redirect:/admin/edit-category/"+ categoryRequest.getId();
        }
    }

    @GetMapping("/delete-category/{id}")
    public String deleteCategory(Model model, @PathVariable(value = "id") Long id) {
        if (categoryService.deleteCategory(id)) {
            model.addAttribute("message", "Category deleted successfully");
        }
        return "redirect:/admin/categories";
    }

}
