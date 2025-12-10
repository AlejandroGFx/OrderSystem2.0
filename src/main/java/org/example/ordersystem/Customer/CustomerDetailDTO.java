package org.example.ordersystem.Customer;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.example.ordersystem.Order.Order;

import java.util.Set;

public record CustomerDetailDTO(
        Long id,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Email String email,
        Set<Order> orders
) {
}
