package com.hcmute.tech_shop.controllers.admin;

import com.hcmute.tech_shop.dtos.requests.CategoryDTO;
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
        List<CategoryDTO> categoryDTOList = categoryService.findAll();
        model.addAttribute("categories", categoryDTOList);
        return "admin/categories/list-category";
    }

    @GetMapping("/add-category")
    public String addCategory(Model model) {
        CategoryDTO categoryDTO = new CategoryDTO();
        model.addAttribute("category", categoryDTO);
        return "admin/categories/add-category";
    }

    @PostMapping("/insert")
    public String addCategory(@Valid @ModelAttribute("category") CategoryDTO categoryDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/categories/list-category";
        }
        if(categoryService.addCategory(categoryDTO)) {
            return "redirect:/admin/categories";
        }
        return "admin/categories/add-category";
    }

    @GetMapping("/edit-category/{id}")
    public String editCategory(Model model, @PathVariable(value = "id") Long id) {
        CategoryDTO categoryDTO = categoryService.findById(id).get();
        model.addAttribute("category", categoryDTO);
        return "admin/categories/edit-category";
    }

    @PostMapping("/edit-category")
    public String editCategory(Model model, @ModelAttribute("category") CategoryDTO categoryDTO) {
        if (categoryService.updateCategory(categoryDTO)) {
            return "redirect:/admin/categories";
        }
        else {
            model.addAttribute("category", categoryDTO);
            return "redirect:/admin/edit-category/"+categoryDTO.getId();
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
