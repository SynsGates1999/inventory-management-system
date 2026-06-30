package com.inventory.inventory_management_system.mapper;

import com.inventory.inventory_management_system.dto.ProductRequest;
import com.inventory.inventory_management_system.dto.ProductResponse;
import com.inventory.inventory_management_system.entity.Category;
import com.inventory.inventory_management_system.entity.Product;
import com.inventory.inventory_management_system.entity.Supplier;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest request, Category category, Supplier supplier) {
        if (request == null) {
            return null;
        }

        return Product.builder()
                .sku(request.getSku())
                .name(request.getName())
                .description(request.getDescription())
                .unitPrice(request.getUnitPrice())
                .reorderThreshold(request.getReorderThreshold())
                .category(category)
                .supplier(supplier)
                .build();
    }

    public ProductResponse toResponse(Product product) {
        if (product == null) {
            return null;
        }

        Category category = product.getCategory();
        Supplier supplier = product.getSupplier();

        return ProductResponse.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .categoryId(category != null ? category.getId() : null)
                .categoryName(category != null ? category.getName() : null)
                .supplierId(supplier != null ? supplier.getId() : null)
                .supplierName(supplier != null ? supplier.getName() : null)
                .unitPrice(product.getUnitPrice())
                .reorderThreshold(product.getReorderThreshold())
                .isActive(product.getIsActive())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}