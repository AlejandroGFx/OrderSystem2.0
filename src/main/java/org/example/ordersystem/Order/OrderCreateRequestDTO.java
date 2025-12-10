package org.example.ordersystem.Order;

import jakarta.validation.constraints.NotEmpty;
import org.example.ordersystem.OrderItem.OrderItemCreateRequestDTO;

import java.util.List;

public record OrderCreateRequestDTO(

        @NotEmpty(message = "Debe enviar al menos un producto")
        List<OrderItemCreateRequestDTO> items
)
{}
