package org.example.ordersystem.Security;

public record AuthRequestDTO(
        String username,
        String password
) {
}
