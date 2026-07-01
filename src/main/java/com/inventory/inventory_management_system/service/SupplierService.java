package com.inventory.inventory_management_system.service;

import com.inventory.inventory_management_system.dto.SupplierRequest;
import com.inventory.inventory_management_system.dto.SupplierResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SupplierService {

    SupplierResponse createSupplier(SupplierRequest request);

    SupplierResponse getSupplierById(Long id);

    Page<SupplierResponse> getAllSuppliers(Pageable pageable);

    SupplierResponse updateSupplier(Long id, SupplierRequest request);

    void deleteSupplier(Long id);
}