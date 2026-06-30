package com.inventory.inventory_management_system.dto;

import jakarta.validation.constraints.Email;
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
public class SupplierRequest {

    @NotBlank(message = "Nama supplier tidak boleh kosong")
    @Size(max = 150, message = "Nama supplier maksimal 150 karakter")
    private String name;

    @Email(message = "Format email tidak valid")
    private String contactEmail;

    private String contactPhone;

    private String address;

    private Boolean isActive;
}