package com.example.demoj2ee.model;

import lombok.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@AllArgsConstructor

public class Product {
    private int id;
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    @Length(min = 0, max = 255, message = "Tên hinh ảnh phải có độ dài từ 0 đến 255 ký tự")
    private String image;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @Min(value = 0, message = "Giá sản phẩm phải lớn hơn hoặc bằng 0")
    @Max(value = 1000000000, message = "Giá sản phẩm phải nhỏ hơn hoặc bằng 1000000000")
    private long price;

    private Category category;

}
