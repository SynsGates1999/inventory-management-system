package com.inventory.inventory_management_system.service;

import com.inventory.inventory_management_system.dto.UserRequest;
import com.inventory.inventory_management_system.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponse createUser(UserRequest request);

    UserResponse getUserById(Long id);

    Page<UserResponse> getAllUsers(Pageable pageable);

    UserResponse updateUser(Long id, UserRequest request);

    void deleteUser(Long id);
}