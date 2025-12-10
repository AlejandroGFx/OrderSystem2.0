package org.example.ordersystem.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    public Boolean existsProductByName(String name);
    public Boolean existsProductByProvider(String provider);

    Page<Product> findAll(Pageable pageable);
}
