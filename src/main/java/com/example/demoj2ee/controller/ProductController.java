package com.example.demoj2ee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demoj2ee.model.Category;
import com.example.demoj2ee.model.Product;
import com.example.demoj2ee.model.ProductForm;
import com.example.demoj2ee.service.CategoryService;
import com.example.demoj2ee.service.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("listProduct", productService.getAll());
        return "product/products";
    }

    @GetMapping("/products/create")
    public String createForm(Model model) {
        model.addAttribute("productForm", new ProductForm());
        model.addAttribute("listCategory", categoryService.getAll());
        return "product/create";
    }

    @PostMapping("/products/create")
    public String create(
            @Valid @ModelAttribute("productForm") ProductForm productForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        Category category = resolveCategory(productForm, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("listCategory", categoryService.getAll());
            return "product/create";
        }

        Product newProduct = new Product();
        newProduct.setName(productForm.getName().trim());
        newProduct.setPrice(productForm.getPrice());
        newProduct.setCategory(category);
        if (productForm.getImageUrl() != null && !productForm.getImageUrl().isBlank()) {
            newProduct.setImage(productForm.getImageUrl().trim());
        }

        try {
            productService.updateImage(newProduct, productForm.getImageFile());
        } catch (IllegalArgumentException ex) {
            bindingResult.addError(new FieldError("productForm", "imageFile", ex.getMessage()));
            model.addAttribute("listCategory", categoryService.getAll());
            return "product/create";
        }

        productService.add(newProduct);
        redirectAttributes.addFlashAttribute("message", "Them san pham thanh cong.");
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Product product = productService.get(id);
        if (product == null) {
            redirectAttributes.addFlashAttribute("error", "Khong tim thay san pham.");
            return "redirect:/products";
        }

        model.addAttribute("productForm", ProductForm.fromProduct(product));
        model.addAttribute("listCategory", categoryService.getAll());
        model.addAttribute("existingImage", product.getImage());
        model.addAttribute("productId", product.getId());
        return "product/edit";
    }

    @PostMapping("/products/edit/{id}")
    public String edit(
            @PathVariable int id,
            @Valid @ModelAttribute("productForm") ProductForm productForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        Product existingProduct = productService.get(id);
        if (existingProduct == null) {
            redirectAttributes.addFlashAttribute("error", "Khong tim thay san pham.");
            return "redirect:/products";
        }

        Category category = resolveCategory(productForm, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("listCategory", categoryService.getAll());
            model.addAttribute("existingImage", existingProduct.getImage());
            model.addAttribute("productId", id);
            return "product/edit";
        }

        Product updatedProduct = new Product();
        updatedProduct.setId(id);
        updatedProduct.setName(productForm.getName().trim());
        updatedProduct.setPrice(productForm.getPrice());
        updatedProduct.setCategory(category);
        updatedProduct.setImage(existingProduct.getImage());

        if (productForm.getImageUrl() != null && !productForm.getImageUrl().isBlank()) {
            updatedProduct.setImage(productForm.getImageUrl().trim());
        }

        try {
            productService.updateImage(updatedProduct, productForm.getImageFile());
        } catch (IllegalArgumentException ex) {
            bindingResult.addError(new FieldError("productForm", "imageFile", ex.getMessage()));
            model.addAttribute("listCategory", categoryService.getAll());
            model.addAttribute("existingImage", existingProduct.getImage());
            model.addAttribute("productId", id);
            return "product/edit";
        }

        productService.update(updatedProduct);
        redirectAttributes.addFlashAttribute("message", "Cap nhat san pham thanh cong.");
        return "redirect:/products";
    }

    @GetMapping("/products/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Product existingProduct = productService.get(id);
        if (existingProduct == null) {
            redirectAttributes.addFlashAttribute("error", "Khong tim thay san pham.");
            return "redirect:/products";
        }

        productService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Xoa san pham thanh cong.");
        return "redirect:/products";
    }

    private Category resolveCategory(ProductForm productForm, BindingResult bindingResult) {
        if (productForm.getCategoryId() == null) {
            return null;
        }

        Category category = categoryService.getById(productForm.getCategoryId());
        if (category == null) {
            bindingResult.addError(new FieldError("productForm", "categoryId", "Danh muc khong hop le"));
        }
        return category;
    }
}
