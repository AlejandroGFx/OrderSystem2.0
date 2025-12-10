package org.example.ordersystem.Security;

public record AuthResponseDTO(
        String token,
        long expiresInMs
) {
}
