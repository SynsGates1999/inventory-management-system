package com.inventory.inventory_management_system.service;

import com.inventory.inventory_management_system.dto.StockItemResponse;
import com.inventory.inventory_management_system.dto.StockMovementResponse;
import com.inventory.inventory_management_system.entity.MovementType;
import com.inventory.inventory_management_system.entity.Product;
import com.inventory.inventory_management_system.entity.StockItem;
import com.inventory.inventory_management_system.entity.StockMovement;
import com.inventory.inventory_management_system.entity.User;
import com.inventory.inventory_management_system.entity.Warehouse;
import com.inventory.inventory_management_system.exception.InsufficientStockException;
import com.inventory.inventory_management_system.exception.ResourceNotFoundException;
import com.inventory.inventory_management_system.mapper.StockItemMapper;
import com.inventory.inventory_management_system.mapper.StockMovementMapper;
import com.inventory.inventory_management_system.repository.ProductRepository;
import com.inventory.inventory_management_system.repository.StockItemRepository;
import com.inventory.inventory_management_system.repository.StockMovementRepository;
import com.inventory.inventory_management_system.repository.UserRepository;
import com.inventory.inventory_management_system.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockItemRepository stockItemRepository;
    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final UserRepository userRepository;
    private final StockItemMapper stockItemMapper;
    private final StockMovementMapper stockMovementMapper;

    @Override
    @Transactional
    public StockItemResponse addStock(Long productId,
                                      Long warehouseId,
                                      Integer quantity,
                                      Long userId,
                                      String referenceType,
                                      Long referenceId,
                                      String notes) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", warehouseId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        StockItem stockItem = stockItemRepository
                .findByProductIdAndWarehouseId(productId, warehouseId)
                .orElseGet(() -> {
                    log.info("StockItem belum ada untuk productId={} warehouseId={}, membuat baru.",
                            productId, warehouseId);
                    return StockItem.builder()
                            .product(product)
                            .warehouse(warehouse)
                            .quantity(0)
                            .build();
                });

        stockItem.setQuantity(stockItem.getQuantity() + quantity);
        stockItem.setUpdatedAt(LocalDateTime.now());

        StockItem savedStockItem = stockItemRepository.save(stockItem);

        StockMovement movement = StockMovement.builder()
                .stockItem(savedStockItem)
                .user(user)
                .movementType(MovementType.IN)
                .quantity(quantity)
                .referenceType(referenceType)
                .referenceId(referenceId)
                .notes(notes)
                .build();

        stockMovementRepository.save(movement);

        log.info("Stok berhasil ditambah: productId={}, warehouseId={}, qty={}, totalBaru={}",
                productId, warehouseId, quantity, savedStockItem.getQuantity());

        return stockItemMapper.toResponse(savedStockItem);
    }

    @Override
    @Transactional
    public StockItemResponse reduceStock(Long productId,
                                         Long warehouseId,
                                         Integer quantity,
                                         Long userId,
                                         String referenceType,
                                         Long referenceId,
                                         String notes) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", warehouseId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        StockItem stockItem = stockItemRepository
                .findByProductIdAndWarehouseId(productId, warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "StockItem",
                        "productId + warehouseId",
                        productId + " + " + warehouseId
                ));

        if (stockItem.getQuantity() < quantity) {
            throw new InsufficientStockException(
                    product.getName(),
                    warehouse.getName(),
                    quantity,
                    stockItem.getQuantity()
            );
        }

        stockItem.setQuantity(stockItem.getQuantity() - quantity);
        stockItem.setUpdatedAt(LocalDateTime.now());

        StockItem savedStockItem = stockItemRepository.save(stockItem);

        StockMovement movement = StockMovement.builder()
                .stockItem(savedStockItem)
                .user(user)
                .movementType(MovementType.OUT)
                .quantity(quantity)
                .referenceType(referenceType)
                .referenceId(referenceId)
                .notes(notes)
                .build();

        stockMovementRepository.save(movement);

        log.info("Stok berhasil dikurangi: productId={}, warehouseId={}, qty={}, sisaStok={}",
                productId, warehouseId, quantity, savedStockItem.getQuantity());

        return stockItemMapper.toResponse(savedStockItem);
    }

    @Override
    @Transactional(readOnly = true)
    public StockItemResponse getStockByProductAndWarehouse(Long productId, Long warehouseId) {
        StockItem stockItem = stockItemRepository
                .findByProductIdAndWarehouseId(productId, warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "StockItem",
                        "productId + warehouseId",
                        productId + " + " + warehouseId
                ));

        return stockItemMapper.toResponse(stockItem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockItemResponse> getStockByWarehouse(Long warehouseId) {
        warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", warehouseId));

        return stockItemRepository.findByWarehouseId(warehouseId)
                .stream()
                .map(stockItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockItemResponse> getStockByProduct(Long productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        return stockItemRepository.findByProductId(productId)
                .stream()
                .map(stockItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockMovementResponse> getAllMovements(Pageable pageable) {
        return stockMovementRepository.findAll(pageable)
                .map(stockMovementMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockMovementResponse> getMovementsByProduct(Long productId, Pageable pageable) {
        productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        return stockMovementRepository.findByStockItemProductId(productId, pageable)
                .map(stockMovementMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockMovementResponse> getMovementsByWarehouse(Long warehouseId,
                                                               Pageable pageable) {
        warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", warehouseId));

        return stockMovementRepository.findByStockItemWarehouseId(warehouseId, pageable)
                .map(stockMovementMapper::toResponse);
    }
}