package org.example.ordersystem.OrderItem;

import org.example.ordersystem.Order.Order;
import org.example.ordersystem.Order.OrderResumeDTO;
import org.example.ordersystem.Product.Product;
import org.example.ordersystem.Product.ProductDetailDTO;
import org.example.ordersystem.Product.ProductResumeDTO;

import java.math.BigDecimal;

public record OrderItemDTO(
        Long id,
        ProductDetailDTO product,
        OrderResumeDTO order,
        Integer quantity,
        BigDecimal unitPrice
) {
}
