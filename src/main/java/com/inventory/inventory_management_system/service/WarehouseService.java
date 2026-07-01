package com.inventory.inventory_management_system.service;

import com.inventory.inventory_management_system.dto.WarehouseRequest;
import com.inventory.inventory_management_system.dto.WarehouseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WarehouseService {

    WarehouseResponse createWarehouse(WarehouseRequest request);

    WarehouseResponse getWarehouseById(Long id);

    Page<WarehouseResponse> getAllWarehouses(Pageable pageable);

    WarehouseResponse updateWarehouse(Long id, WarehouseRequest request);

    void deleteWarehouse(Long id);
}