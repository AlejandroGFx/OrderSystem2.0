package org.example.ordersystem.Order;


import jakarta.transaction.Transactional;
import org.example.ordersystem.Customer.Customer;
import org.example.ordersystem.Customer.CustomerRepository;
import org.example.ordersystem.Errors.ResourceNotFoundException;
import org.example.ordersystem.OrderItem.OrderItem;
import org.example.ordersystem.OrderItem.OrderItemCreateRequestDTO;
import org.example.ordersystem.Product.Product;
import org.example.ordersystem.Product.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository,  CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Order createOrder(Long customerId, OrderCreateRequestDTO orderCreateRequestDTO) throws ResourceNotFoundException{
        Order order  = new Order();
        Customer customer = this.customerRepository.findById(customerId)
                .orElseThrow(()->new ResourceNotFoundException("Customer Not Found"));
        Set<OrderItem> orderItems = order.getOrderItems();
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItemCreateRequestDTO orderItemCreateRequestDTO : orderCreateRequestDTO.items()){
            Product product = this.productRepository.findById(orderItemCreateRequestDTO.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            int quantity = orderItemCreateRequestDTO.quantity();
            BigDecimal unitPrice = product.getPrice();
            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(quantity));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemCreateRequestDTO.quantity());
            orderItem.setUnitPrice(product.getPrice());

            orderItems.add(orderItem);

            total = total.add(lineTotal);
        }
        order.setOrderItems(orderItems);
        order.setCustomer(customer);
        order.setTotal(total);
        return this.orderRepository.save(order);
    }
}
