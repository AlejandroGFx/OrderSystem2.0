package org.example.ordersystem.OrderItem;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.example.ordersystem.Order.Order;
import org.example.ordersystem.Product.Product;

import java.math.BigDecimal;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "product_id",
            foreignKey = @ForeignKey(name = "fk_orderItem_product")
    )
    @NotNull
    private Product product;

    @ManyToOne
    @JoinColumn(
            name = "order_id",
            foreignKey = @ForeignKey(name = "fk_orderItem_order")
    )
    @NotNull
    private Order order;

    @PositiveOrZero
    private Integer quantity;

    private BigDecimal unitPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public @PositiveOrZero Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(@PositiveOrZero Integer quantity) {
        this.quantity = quantity;
    }

    public @NotNull Order getOrder() {
        return order;
    }

    public void setOrder(@NotNull Order order) {
        this.order = order;
    }

    public @NotNull Product getProduct() {
        return product;
    }

    public void setProduct(@NotNull Product product) {
        this.product = product;
    }
}
