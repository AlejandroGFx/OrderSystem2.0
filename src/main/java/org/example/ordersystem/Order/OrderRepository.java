package org.example.ordersystem.Order;

import org.example.ordersystem.Customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
@EntityGraph("customer")
public Page<Order> findAll( Pageable pageable);
    @EntityGraph(attributePaths = {"customer"})
    Optional<Order> findWithCustomerById(Long id);

    @Query("select o.customer.id from Order o where o.id = :orderId")
    public Optional<Long> findCustomerIdById(Long orderId);
}

