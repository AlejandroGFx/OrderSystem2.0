package org.example.ordersystem.Product;

public record ProductDetailDTO(
        Long id,
        String name,
        java.math.BigDecimal price,
        String provider
) {
}
