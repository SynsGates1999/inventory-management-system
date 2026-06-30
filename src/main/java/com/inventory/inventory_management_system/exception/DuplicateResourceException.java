package com.inventory.inventory_management_system.exception;

public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s dengan %s: '%s' sudah terdaftar", resourceName, fieldName, fieldValue));
    }
}