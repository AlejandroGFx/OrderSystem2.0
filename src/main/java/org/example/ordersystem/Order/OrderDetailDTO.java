package org.example.ordersystem.Order;

import org.example.ordersystem.Customer.CustomerResumeDTO;
import org.example.ordersystem.OrderItem.OrderItem;
import org.example.ordersystem.OrderItem.OrderItemDTO;

import java.math.BigDecimal;
import java.util.Set;

public record OrderDetailDTO(
        Long id,
        OrderStatus orderStatus,
        BigDecimal total,
        CustomerResumeDTO customer,
        Set<OrderItemDTO> orderItems
) {
}
