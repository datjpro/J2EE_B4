package com.example.demoj2ee.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private int id;

    @NotBlank(message = "Ten danh muc khong duoc de trong")
    private String name;
}
