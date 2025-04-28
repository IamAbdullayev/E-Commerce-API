package com.abdullayev.demoshops.dto;

import com.abdullayev.demoshops.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;


@Data
public class OrderDto {
    private Long id;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private Set<OrderItemDto> items;
}
