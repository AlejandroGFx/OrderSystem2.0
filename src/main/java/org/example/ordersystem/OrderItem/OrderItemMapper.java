package org.example.ordersystem.OrderItem;

import org.example.ordersystem.Order.OrderMapper;
import org.example.ordersystem.Product.ProductMapper;

import java.util.HashSet;
import java.util.Set;

public class OrderItemMapper {

    public static OrderItemDTO toOrderItemDTO(OrderItem orderItem) {
        return new OrderItemDTO(
                orderItem.getId(),
                ProductMapper.toProductDetailDTO(orderItem.getProduct()),
                OrderMapper.orderToOrderResumeDTO(orderItem.getOrder()),
                orderItem.getQuantity(),
                orderItem.getUnitPrice()
        );
    }

    public static Set<OrderItemDTO> setOfOrderItemsToSetOfOrderItemDTO(Set<OrderItem> orderItemSet) {
        Set<OrderItemDTO> setDTO = new HashSet<>();
        for (OrderItem orderItem : orderItemSet) {
            setDTO.add(OrderItemMapper.toOrderItemDTO(orderItem));
        }
        return setDTO;
    }
}
