package com.example.demoj2ee.model;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductForm {
    @NotBlank(message = "Ten san pham khong duoc de trong")
    @Size(max = 255, message = "Ten san pham toi da 255 ky tu")
    private String name;

    @Size(max = 255, message = "Duong dan hinh anh toi da 255 ky tu")
    private String imageUrl;

    @NotNull(message = "Gia san pham khong duoc de trong")
    @PositiveOrZero(message = "Gia san pham phai lon hon hoac bang 0")
    @Max(value = 1000000000, message = "Gia san pham phai nho hon hoac bang 1000000000")
    private Long price;

    @NotNull(message = "Danh muc khong duoc de trong")
    private Integer categoryId;

    private MultipartFile imageFile;

    public static ProductForm fromProduct(Product product) {
        ProductForm form = new ProductForm();
        form.setName(product.getName());
        form.setPrice(product.getPrice());
        if (product.getCategory() != null) {
            form.setCategoryId(product.getCategory().getId());
        }
        return form;
    }
}
