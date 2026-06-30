package com.inventory.inventory_management_system.mapper;

import com.inventory.inventory_management_system.dto.UserRequest;
import com.inventory.inventory_management_system.dto.UserResponse;
import com.inventory.inventory_management_system.entity.Role;
import com.inventory.inventory_management_system.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequest request, Role role) {
        if (request == null) {
            return null;
        }

        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(request.getPassword())
                .fullName(request.getFullName())
                .role(role)
                .build();
    }

    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .roleName(user.getRole() != null ? user.getRole().getName() : null)
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}