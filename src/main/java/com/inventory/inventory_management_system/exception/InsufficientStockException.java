package com.inventory.inventory_management_system.exception;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String message) {
        super(message);
    }

    public InsufficientStockException(String productName,
                                      String warehouseName,
                                      Integer requested,
                                      Integer available) {
        super(String.format(
                "Stok tidak mencukupi untuk produk '%s' di gudang '%s'. " +
                        "Diminta: %d, Tersedia: %d",
                productName, warehouseName, requested, available
        ));
    }
}