package org.example.ordersystem.User;

public record RegisterRequest(
        String username,
        String password,
        UserRole role,
        Long customerId
) {
}
