package com.hcmute.tech_shop.controllers.admin;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
//    @Autowired
//    ICategoryService iCategoryService;
//
//    @GetMapping("") // localhost:8080/admin/categories?page=1&limit=10
//    public String index(Model model, @RequestParam("page") int page, @RequestParam("limit") int limit) {
//        List<Category> categories = iCategoryService.findAll();
//        model.addAttribute("categories", categories);
//        return "admin/categories/index";
//    }
//
//    @GetMapping("/add")
//    public String add(ModelMap model) {
//        CategoryDTO categoryDTO = new CategoryDTO();
//        categoryDTO.setIsEdit(false);
//        // Add categoryDTO to model attribute with key "category" to pass data from controller to view
//        model.addAttribute("category", categoryDTO);
//        return "admin/categories/add";
//    }
//
//    @GetMapping("/edit/{id}")
//    public ModelAndView edit(ModelMap model, @PathVariable("id") Long id) {
//        Optional<Category> category = iCategoryService.findById(id);
//        CategoryDTO categoryDTO = new CategoryDTO();
//        if (category.isPresent()) { // if category is found
//            Category entity = category.get();
//            // Copy properties from entity to categoryDTO
//            BeanUtils.copyProperties(entity, categoryDTO);
//            categoryDTO.setIsEdit(true);
//            // Add categoryDTO to model attribute with key "category" to pass data from controller to view
//            model.addAttribute("category", categoryDTO);
//            // Return view with model attribute
//            return new ModelAndView("admin/category/add", model);
//        }
//        model.addAttribute("message", "Không tìm thấy danh mục");
//        return new ModelAndView("redirect:/admin/categories", model);
//    }
//
//    @GetMapping("/delete/{id}")
//    public ModelAndView delete(ModelMap model, @PathVariable("id") Long id) {
//        iCategoryService.deleteById(id);
//        model.addAttribute("message", "Xóa danh mục thành công");
//        // Return view with model attribute
//        return new ModelAndView("redirect:/admin/categories", model);
//    }
//
//    @PostMapping("/insert")
//    public ModelAndView saveOrUpdate(ModelMap model,
//                                     @Valid @ModelAttribute("category") CategoryDTO categoryDTO,
//                                     BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return new ModelAndView("admin/category/add");
//        }
//
//        Category category = new Category();
//        // Copy properties from categoryDTO to category
//        BeanUtils.copyProperties(categoryDTO, category);
//        // Save category to database
//        iCategoryService.save(category);
//
//        if (categoryDTO.getIsEdit() == true){
//            model.addAttribute("message", "Category updated successfully");
//        }
//        else {
//            model.addAttribute("message", "Category is save");
//        }
//
//        return new ModelAndView("redirect:/admin/categories", model);
//    }
}
