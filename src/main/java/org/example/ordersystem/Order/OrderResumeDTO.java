package org.example.ordersystem.Order;

import java.math.BigDecimal;

public record OrderResumeDTO(
        Long id,
OrderStatus orderStatus,
    BigDecimal total
) {
}
