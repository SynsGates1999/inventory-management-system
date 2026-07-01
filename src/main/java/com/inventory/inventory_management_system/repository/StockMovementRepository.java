package com.inventory.inventory_management_system.repository;

import com.inventory.inventory_management_system.entity.MovementType;
import com.inventory.inventory_management_system.entity.StockMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    Page<StockMovement> findAll(Pageable pageable);

    Page<StockMovement> findByStockItemId(Long stockItemId, Pageable pageable);

    Page<StockMovement> findByStockItemProductId(Long productId, Pageable pageable);

    Page<StockMovement> findByStockItemWarehouseId(Long warehouseId, Pageable pageable);

    Page<StockMovement> findByMovementType(MovementType movementType, Pageable pageable);

    Page<StockMovement> findByUserId(Long userId, Pageable pageable);
}