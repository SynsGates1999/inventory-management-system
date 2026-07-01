package com.inventory.inventory_management_system.service;

import com.inventory.inventory_management_system.dto.ProductRequest;
import com.inventory.inventory_management_system.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse getProductById(Long id);

    Page<ProductResponse> getAllProducts(Pageable pageable);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

    List<ProductResponse> searchProductsByName(String name);

    List<ProductResponse> getProductsByCategoryId(Long categoryId);
}