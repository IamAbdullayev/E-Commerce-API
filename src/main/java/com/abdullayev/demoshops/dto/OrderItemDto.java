package com.abdullayev.demoshops.dto;

import lombok.Data;
import java.math.BigDecimal;


@Data
public class OrderItemDto {
    private Long id;
    private String productName;
    private String productBrand;
    private Integer quantity;
    private BigDecimal price;
}
