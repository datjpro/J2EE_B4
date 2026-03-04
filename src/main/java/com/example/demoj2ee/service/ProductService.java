package com.example.demoj2ee.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demoj2ee.model.Product;

@Service
public class ProductService {
    private final List<Product> listProduct = new ArrayList<>();

    public List<Product> getAll() {
        return listProduct;
    }

    public Product get(int id) {
        return listProduct.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public void add(Product newProduct) {
        int maxId = listProduct.stream().mapToInt(Product::getId).max().orElse(0);
        newProduct.setId(maxId + 1);
        listProduct.add(newProduct);
    }

    public void update(Product editProduct) {
        Product find = get(editProduct.getId());
        if (find != null) {
            find.setPrice(editProduct.getPrice());
            find.setName(editProduct.getName());
            find.setCategory(editProduct.getCategory());
            if (editProduct.getImage() != null && !editProduct.getImage().isBlank()) {
                find.setImage(editProduct.getImage());
            }
        }
    }

    public void updateImage(Product newProduct, MultipartFile imageProduct) {
        if (imageProduct == null || imageProduct.isEmpty()) {
            return;
        }

        String contentType = imageProduct.getContentType();
        if (contentType != null && !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("File upload phai la hinh anh.");
        }

        try {
            Path dirImages = Paths.get("src/main/resources/static/images");
            if (!Files.exists(dirImages)) {
                Files.createDirectories(dirImages);
            }

            String originalFileName = StringUtils.cleanPath(imageProduct.getOriginalFilename());
            String newFileName = UUID.randomUUID() + "_" + originalFileName;
            Path pathFileUpload = dirImages.resolve(newFileName);
            Files.copy(imageProduct.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);
            newProduct.setImage(newFileName);
        } catch (Exception e) {
            throw new RuntimeException("Khong the luu hinh anh.", e);
        }
    }

    public void delete(int id) {
        Product find = get(id);
        if (find != null) {
            listProduct.remove(find);
        }
    }

    public long countByCategoryId(int categoryId) {
        return listProduct.stream()
                .filter(p -> p.getCategory() != null && p.getCategory().getId() == categoryId)
                .count();
    }
}
