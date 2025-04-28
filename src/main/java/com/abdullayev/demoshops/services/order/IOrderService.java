package com.abdullayev.demoshops.services.order;

import com.abdullayev.demoshops.dto.OrderDto;
import com.abdullayev.demoshops.models.Order;

import java.util.List;

public interface IOrderService {
    OrderDto placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getOrdersByUserId(Long userId);
    OrderDto convertToDto(Order order);
}
