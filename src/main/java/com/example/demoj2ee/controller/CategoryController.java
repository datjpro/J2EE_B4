package com.example.demoj2ee.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demoj2ee.model.Category;
import com.example.demoj2ee.service.CategoryService;
import com.example.demoj2ee.service.ProductService;

@Controller
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        Map<Integer, Long> productCountByCategory = new LinkedHashMap<>();
        for (Category category : categoryService.getAll()) {
            productCountByCategory.put(category.getId(), productService.countByCategoryId(category.getId()));
        }

        model.addAttribute("listCategory", categoryService.getAll());
        model.addAttribute("productCountByCategory", productCountByCategory);
        return "category/categories";
    }
}
