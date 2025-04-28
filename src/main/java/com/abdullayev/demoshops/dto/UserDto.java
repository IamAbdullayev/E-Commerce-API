package com.abdullayev.demoshops.dto;

import lombok.Data;
import java.util.List;


@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private CartDto cart;
    private List<OrderDto> orders;
}
