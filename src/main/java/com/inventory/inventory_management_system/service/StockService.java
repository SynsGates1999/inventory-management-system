package com.inventory.inventory_management_system.service;

import com.inventory.inventory_management_system.dto.StockItemResponse;
import com.inventory.inventory_management_system.dto.StockMovementResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StockService {

    StockItemResponse addStock(Long productId,
                               Long warehouseId,
                               Integer quantity,
                               Long userId,
                               String referenceType,
                               Long referenceId,
                               String notes);

    StockItemResponse reduceStock(Long productId,
                                  Long warehouseId,
                                  Integer quantity,
                                  Long userId,
                                  String referenceType,
                                  Long referenceId,
                                  String notes);

    StockItemResponse getStockByProductAndWarehouse(Long productId, Long warehouseId);

    List<StockItemResponse> getStockByWarehouse(Long warehouseId);

    List<StockItemResponse> getStockByProduct(Long productId);

    Page<StockMovementResponse> getAllMovements(Pageable pageable);

    Page<StockMovementResponse> getMovementsByProduct(Long productId, Pageable pageable);

    Page<StockMovementResponse> getMovementsByWarehouse(Long warehouseId, Pageable pageable);
}