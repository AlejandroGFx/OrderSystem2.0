package org.example.ordersystem.Order;

import org.example.ordersystem.Customer.CustomerMapper;
import org.example.ordersystem.OrderItem.OrderItemMapper;

import java.util.Collections;
import java.util.Set;

public class OrderMapper {

    public static OrderResumeDTO orderToOrderResumeDTO(Order order){

        return new OrderResumeDTO(
                order.getId(),
                order.getOrderStatus(),
                order.getTotal()
        );
    }

    public static OrderDetailDTO orderToOrderDetailDTO(Order order){

        return new OrderDetailDTO( order.getId(),
                order.getOrderStatus(),
                order.getTotal(),
                CustomerMapper.customerToCustomerResumeDTO(order.getCustomer()),
                OrderItemMapper.setOfOrderItemsToSetOfOrderItemDTO(order.getOrderItems()));
    }
}
