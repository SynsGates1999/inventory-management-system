package com.inventory.inventory_management_system.mapper;

import com.inventory.inventory_management_system.dto.SupplierRequest;
import com.inventory.inventory_management_system.dto.SupplierResponse;
import com.inventory.inventory_management_system.entity.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    public Supplier toEntity(SupplierRequest request) {
        if (request == null) {
            return null;
        }

        return Supplier.builder()
                .name(request.getName())
                .contactEmail(request.getContactEmail())
                .contactPhone(request.getContactPhone())
                .address(request.getAddress())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .build();
    }

    public SupplierResponse toResponse(Supplier supplier) {
        if (supplier == null) {
            return null;
        }

        return SupplierResponse.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .contactEmail(supplier.getContactEmail())
                .contactPhone(supplier.getContactPhone())
                .address(supplier.getAddress())
                .isActive(supplier.getIsActive())
                .createdAt(supplier.getCreatedAt())
                .build();
    }
}