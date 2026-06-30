package com.inventory.inventory_management_system.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    @NotBlank(message = "SKU tidak boleh kosong")
    @Size(max = 50, message = "SKU maksimal 50 karakter")
    private String sku;

    @NotBlank(message = "Nama produk tidak boleh kosong")
    @Size(max = 150, message = "Nama produk maksimal 150 karakter")
    private String name;

    private String description;

    @NotNull(message = "Category ID tidak boleh kosong")
    private Long categoryId;

    private Long supplierId;

    @NotNull(message = "Unit price tidak boleh kosong")
    @DecimalMin(value = "0.0", inclusive = true, message = "Unit price tidak boleh kurang dari 0")
    private BigDecimal unitPrice;

    @Min(value = 0, message = "Reorder threshold tidak boleh kurang dari 0")
    private Integer reorderThreshold;
}