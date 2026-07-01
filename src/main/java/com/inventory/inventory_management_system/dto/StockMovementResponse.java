package com.inventory.inventory_management_system.dto;

import com.inventory.inventory_management_system.entity.MovementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMovementResponse {

    private Long id;
    private Long stockItemId;
    private Long productId;
    private String productName;
    private String productSku;
    private Long warehouseId;
    private String warehouseName;
    private Long userId;
    private String username;
    private MovementType movementType;
    private Integer quantity;
    private String referenceType;
    private Long referenceId;
    private String notes;
    private LocalDateTime createdAt;
}