package com.inventory.inventory_management_system.mapper;

import com.inventory.inventory_management_system.dto.WarehouseRequest;
import com.inventory.inventory_management_system.dto.WarehouseResponse;
import com.inventory.inventory_management_system.entity.Warehouse;
import org.springframework.stereotype.Component;

@Component
public class WarehouseMapper {

    public Warehouse toEntity(WarehouseRequest request) {
        if (request == null) {
            return null;
        }

        return Warehouse.builder()
                .name(request.getName())
                .location(request.getLocation())
                .build();
    }

    public WarehouseResponse toResponse(Warehouse warehouse) {
        if (warehouse == null) {
            return null;
        }

        return WarehouseResponse.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .location(warehouse.getLocation())
                .createdAt(warehouse.getCreatedAt())
                .build();
    }
}