package com.inventory.inventory_management_system.repository;

import com.inventory.inventory_management_system.entity.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockItemRepository extends JpaRepository<StockItem, Long> {

    Optional<StockItem> findByProductIdAndWarehouseId(Long productId, Long warehouseId);

    List<StockItem> findByWarehouseId(Long warehouseId);

    List<StockItem> findByProductId(Long productId);

    @Query("SELECT s FROM StockItem s WHERE s.quantity <= s.product.reorderThreshold")
    List<StockItem> findItemsBelowReorderThreshold();

    @Query("SELECT s FROM StockItem s " +
            "JOIN FETCH s.product p " +
            "JOIN FETCH s.warehouse w " +
            "WHERE s.warehouse.id = :warehouseId")
    List<StockItem> findByWarehouseIdWithDetails(@Param("warehouseId") Long warehouseId);
}