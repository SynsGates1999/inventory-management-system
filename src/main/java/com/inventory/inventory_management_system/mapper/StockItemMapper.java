package com.inventory.inventory_management_system.mapper;

import com.inventory.inventory_management_system.dto.StockItemResponse;
import com.inventory.inventory_management_system.entity.Product;
import com.inventory.inventory_management_system.entity.StockItem;
import com.inventory.inventory_management_system.entity.Warehouse;
import org.springframework.stereotype.Component;

@Component
public class StockItemMapper {

    public StockItemResponse toResponse(StockItem stockItem) {
        if (stockItem == null) {
            return null;
        }

        Product product = stockItem.getProduct();
        Warehouse warehouse = stockItem.getWarehouse();

        return StockItemResponse.builder()
                .id(stockItem.getId())
                .productId(product != null ? product.getId() : null)
                .productName(product != null ? product.getName() : null)
                .productSku(product != null ? product.getSku() : null)
                .warehouseId(warehouse != null ? warehouse.getId() : null)
                .warehouseName(warehouse != null ? warehouse.getName() : null)
                .quantity(stockItem.getQuantity())
                .version(stockItem.getVersion())
                .updatedAt(stockItem.getUpdatedAt())
                .build();
    }
}