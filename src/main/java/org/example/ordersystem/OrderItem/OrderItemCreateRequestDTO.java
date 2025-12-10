package org.example.ordersystem.OrderItem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemCreateRequestDTO(
        @NotNull @NotEmpty(message = "Debe enviar al menos un producto")
        Long productId,
        @Positive @Min(value = 1, message = "Debe ser 1 o mas")
        Integer quantity
) {
}
