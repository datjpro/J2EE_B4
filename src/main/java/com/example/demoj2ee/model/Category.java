package com.example.demoj2ee.model;

import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private int id;
    @NotBlank(message = "Tên danh mục không được để trống")
    private String name;
}
