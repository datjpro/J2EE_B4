package com.example.demoj2ee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demoj2ee.model.Product;

@Service
public class ProductService {
    List<Product> listProduct = new ArrayList<>();

    public List<Product> getAll() {
        return listProduct;
    }

    public Product get(int id) {
        return listProduct.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public void add(Product newProduct) {
        int maxID = listProduct.stream().mapToInt(Product::getId).max().orElse(0);
        newProduct.setId(maxID + 1);
        listProduct.add(newProduct);
    }

    public void update(Product editProduct) {
        Product find = get(editProduct.getId());
        if (find != null) {
            find.setPrice(editProduct.getPrice());
            find.setName(editProduct.getName());
            if(editProduct.getImage() != null) {
                find.setImage(editProduct.getImage());
            }
        }
    }

    public void updateImage(Product newProduct, MultipartFile imageProduct){
        String contentType = imageProduct.getContentType();
        if(contentType != null && IllegalArgumentException("Tệp tải lên không phải hình ảnh!");
        )
    }

}
