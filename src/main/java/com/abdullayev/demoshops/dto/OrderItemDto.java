package com.abdullayev.demoshops.dto;

import lombok.Data;
import java.math.BigDecimal;


@Data
public class OrderItemDto {
    private Long id;
    private Integer quantity;
    private BigDecimal price;
    private ProductDto product;
}
