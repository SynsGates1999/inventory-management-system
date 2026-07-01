package com.inventory.inventory_management_system.controller;

import com.inventory.inventory_management_system.dto.StockAdjustmentRequest;
import com.inventory.inventory_management_system.dto.StockItemResponse;
import com.inventory.inventory_management_system.dto.StockMovementResponse;
import com.inventory.inventory_management_system.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping("/add")
    public ResponseEntity<StockItemResponse> addStock(
            @Valid @RequestBody StockAdjustmentRequest request) {
        StockItemResponse response = stockService.addStock(
                request.getProductId(),
                request.getWarehouseId(),
                request.getQuantity(),
                request.getUserId(),
                request.getReferenceType(),
                request.getReferenceId(),
                request.getNotes()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/reduce")
    public ResponseEntity<StockItemResponse> reduceStock(
            @Valid @RequestBody StockAdjustmentRequest request) {
        StockItemResponse response = stockService.reduceStock(
                request.getProductId(),
                request.getWarehouseId(),
                request.getQuantity(),
                request.getUserId(),
                request.getReferenceType(),
                request.getReferenceId(),
                request.getNotes()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<StockItemResponse> getStockByProductAndWarehouse(
            @RequestParam Long productId,
            @RequestParam Long warehouseId) {
        StockItemResponse response = stockService.getStockByProductAndWarehouse(
                productId, warehouseId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<StockItemResponse>> getStockByWarehouse(
            @PathVariable Long warehouseId) {
        List<StockItemResponse> responses = stockService.getStockByWarehouse(warehouseId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<StockItemResponse>> getStockByProduct(
            @PathVariable Long productId) {
        List<StockItemResponse> responses = stockService.getStockByProduct(productId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/movements")
    public ResponseEntity<Page<StockMovementResponse>> getAllMovements(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {
        Page<StockMovementResponse> responses = stockService.getAllMovements(pageable);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/movements/product/{productId}")
    public ResponseEntity<Page<StockMovementResponse>> getMovementsByProduct(
            @PathVariable Long productId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {
        Page<StockMovementResponse> responses = stockService.getMovementsByProduct(
                productId, pageable);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/movements/warehouse/{warehouseId}")
    public ResponseEntity<Page<StockMovementResponse>> getMovementsByWarehouse(
            @PathVariable Long warehouseId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {
        Page<StockMovementResponse> responses = stockService.getMovementsByWarehouse(
                warehouseId, pageable);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}