package com.inventory.inventory_management_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequest {

    @NotBlank(message = "Nama kategori tidak boleh kosong")
    @Size(max = 100, message = "Nama kategori maksimal 100 karakter")
    private String name;

    private String description;
}