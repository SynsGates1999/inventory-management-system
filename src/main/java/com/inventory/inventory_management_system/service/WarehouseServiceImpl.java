package com.inventory.inventory_management_system.service;

import com.inventory.inventory_management_system.dto.WarehouseRequest;
import com.inventory.inventory_management_system.dto.WarehouseResponse;
import com.inventory.inventory_management_system.entity.Warehouse;
import com.inventory.inventory_management_system.exception.DuplicateResourceException;
import com.inventory.inventory_management_system.exception.ResourceNotFoundException;
import com.inventory.inventory_management_system.mapper.WarehouseMapper;
import com.inventory.inventory_management_system.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;

    @Override
    @Transactional
    public WarehouseResponse createWarehouse(WarehouseRequest request) {
        if (warehouseRepository.findByName(request.getName()).isPresent()) {
            throw new DuplicateResourceException("Warehouse", "name", request.getName());
        }

        Warehouse warehouse = warehouseMapper.toEntity(request);
        Warehouse savedWarehouse = warehouseRepository.save(warehouse);

        return warehouseMapper.toResponse(savedWarehouse);
    }

    @Override
    @Transactional(readOnly = true)
    public WarehouseResponse getWarehouseById(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", id));

        return warehouseMapper.toResponse(warehouse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WarehouseResponse> getAllWarehouses(Pageable pageable) {
        return warehouseRepository.findAll(pageable)
                .map(warehouseMapper::toResponse);
    }

    @Override
    @Transactional
    public WarehouseResponse updateWarehouse(Long id, WarehouseRequest request) {
        Warehouse existingWarehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", id));

        warehouseRepository.findByName(request.getName())
                .filter(found -> !found.getId().equals(id))
                .ifPresent(found -> {
                    throw new DuplicateResourceException("Warehouse", "name", request.getName());
                });

        existingWarehouse.setName(request.getName());
        existingWarehouse.setLocation(request.getLocation());

        Warehouse updatedWarehouse = warehouseRepository.save(existingWarehouse);

        return warehouseMapper.toResponse(updatedWarehouse);
    }

    @Override
    @Transactional
    public void deleteWarehouse(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", id));

        warehouseRepository.delete(warehouse);
    }
}