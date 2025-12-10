package org.example.ordersystem.Order;

import org.apache.coyote.BadRequestException;
import org.example.ordersystem.Customer.CustomerRepository;
import org.example.ordersystem.Errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    OrderRepository orderRepository;
    CustomerRepository customerRepository;
    OrderService orderService;

    public OrderController(OrderRepository orderRepository, CustomerRepository customerRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<Page<OrderResumeDTO>> getAllOrders(Pageable pageable) {
        Page<OrderResumeDTO> page = this.orderRepository.findAll(pageable)
                .map(OrderMapper::orderToOrderResumeDTO);
        return ResponseEntity.ok(page);
    }
@PreAuthorize("@authz.canAccessCustomer(#id)")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> getOrderDetails(@PathVariable Long id) {
        Order order = this.orderRepository.findWithCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
        return ResponseEntity.ok(OrderMapper.orderToOrderDetailDTO(order));
    }
@PreAuthorize("@authz.canAccessCustomer(#customerId)")
    @PostMapping("/{customerId}/makeOrder")
    public ResponseEntity<OrderDetailDTO> createOrder(@RequestBody OrderCreateRequestDTO orderItemList, @PathVariable(name = "customerId") @P("customerId") Long customerId) {
        return ResponseEntity.ok(OrderMapper.orderToOrderDetailDTO(orderService.createOrder(customerId, orderItemList)));

    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> updateOrder(@PathVariable Long id, @RequestBody Order order) throws BadRequestException {

        Order orderToModify = this.orderRepository.findWithCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
        if (!orderToModify.getOrderStatus().equals(OrderStatus.PENDING)) {
            throw new BadRequestException("Order status is " + orderToModify.getOrderStatus().toString());
        }
        orderToModify.setOrderItems(order.getOrderItems());
        orderToModify.setCustomer(order.getCustomer());
        orderToModify.setOrderStatus(order.getOrderStatus());
        this.orderRepository.save(orderToModify);
        return ResponseEntity.ok(OrderMapper.orderToOrderDetailDTO(orderToModify));
    }
    @PreAuthorize("@authz.canAccessCustomer(#id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        this.orderRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
