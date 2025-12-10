package org.example.ordersystem.Customer;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerResumeDTO(
        Long id,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Email String email
) {
}
