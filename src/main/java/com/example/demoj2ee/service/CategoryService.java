package com.example.demoj2ee.service;

import com.example.demoj2ee.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final List<Category> categories = new ArrayList<>();

    public CategoryService() {
        categories.add(new Category(1, "Dien thoai"));
        categories.add(new Category(2, "Laptop"));
        categories.add(new Category(3, "Phu kien"));
    }

    public List<Category> getAll() {
        return categories;
    }

    public Category getById(int id) {
        Optional<Category> result = categories.stream().filter(c -> c.getId() == id).findFirst();
        return result.orElse(null);
    }
}
