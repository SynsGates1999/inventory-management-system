package com.inventory.inventory_management_system.mapper;

import com.inventory.inventory_management_system.dto.StockMovementResponse;
import com.inventory.inventory_management_system.entity.Product;
import com.inventory.inventory_management_system.entity.StockItem;
import com.inventory.inventory_management_system.entity.StockMovement;
import com.inventory.inventory_management_system.entity.User;
import com.inventory.inventory_management_system.entity.Warehouse;
import org.springframework.stereotype.Component;

@Component
public class StockMovementMapper {

    public StockMovementResponse toResponse(StockMovement movement) {
        if (movement == null) {
            return null;
        }

        StockItem stockItem = movement.getStockItem();
        User user = movement.getUser();

        Product product = (stockItem != null) ? stockItem.getProduct() : null;
        Warehouse warehouse = (stockItem != null) ? stockItem.getWarehouse() : null;

        return StockMovementResponse.builder()
                .id(movement.getId())
                .stockItemId(stockItem != null ? stockItem.getId() : null)
                .productId(product != null ? product.getId() : null)
                .productName(product != null ? product.getName() : null)
                .productSku(product != null ? product.getSku() : null)
                .warehouseId(warehouse != null ? warehouse.getId() : null)
                .warehouseName(warehouse != null ? warehouse.getName() : null)
                .userId(user != null ? user.getId() : null)
                .username(user != null ? user.getUsername() : null)
                .movementType(movement.getMovementType())
                .quantity(movement.getQuantity())
                .referenceType(movement.getReferenceType())
                .referenceId(movement.getReferenceId())
                .notes(movement.getNotes())
                .createdAt(movement.getCreatedAt())
                .build();
    }
}