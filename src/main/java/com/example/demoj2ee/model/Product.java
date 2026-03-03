package com.example.demoj2ee.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int id;

    @NotBlank(message = "Ten san pham khong duoc de trong")
    @Size(max = 255, message = "Ten san pham toi da 255 ky tu")
    private String name;

    @Size(max = 255, message = "Ten hinh anh toi da 255 ky tu")
    private String image;

    @NotNull(message = "Gia san pham khong duoc de trong")
    @PositiveOrZero(message = "Gia san pham phai lon hon hoac bang 0")
    @Max(value = 1000000000, message = "Gia san pham phai nho hon hoac bang 1000000000")
    private Long price;

    @NotNull(message = "Danh muc khong duoc de trong")
    private Category category;
}
