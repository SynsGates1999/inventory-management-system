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
public class WarehouseRequest {

    @NotBlank(message = "Nama gudang tidak boleh kosong")
    @Size(max = 150, message = "Nama gudang maksimal 150 karakter")
    private String name;

    @Size(max = 255, message = "Lokasi gudang maksimal 255 karakter")
    private String location;
}