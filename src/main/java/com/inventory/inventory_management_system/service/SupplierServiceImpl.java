package com.inventory.inventory_management_system.service;

import com.inventory.inventory_management_system.dto.SupplierRequest;
import com.inventory.inventory_management_system.dto.SupplierResponse;
import com.inventory.inventory_management_system.entity.Supplier;
import com.inventory.inventory_management_system.exception.ResourceNotFoundException;
import com.inventory.inventory_management_system.mapper.SupplierMapper;
import com.inventory.inventory_management_system.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    @Override
    @Transactional
    public SupplierResponse createSupplier(SupplierRequest request) {
        Supplier supplier = supplierMapper.toEntity(request);
        Supplier savedSupplier = supplierRepository.save(supplier);

        return supplierMapper.toResponse(savedSupplier);
    }

    @Override
    @Transactional(readOnly = true)
    public SupplierResponse getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", id));

        return supplierMapper.toResponse(supplier);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupplierResponse> getAllSuppliers(Pageable pageable) {
        return supplierRepository.findAll(pageable)
                .map(supplierMapper::toResponse);
    }

    @Override
    @Transactional
    public SupplierResponse updateSupplier(Long id, SupplierRequest request) {
        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", id));

        existingSupplier.setName(request.getName());
        existingSupplier.setContactEmail(request.getContactEmail());
        existingSupplier.setContactPhone(request.getContactPhone());
        existingSupplier.setAddress(request.getAddress());

        if (request.getIsActive() != null) {
            existingSupplier.setIsActive(request.getIsActive());
        }

        Supplier updatedSupplier = supplierRepository.save(existingSupplier);

        return supplierMapper.toResponse(updatedSupplier);
    }

    @Override
    @Transactional
    public void deleteSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", "id", id));

        supplierRepository.delete(supplier);
    }
}