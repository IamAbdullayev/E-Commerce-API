package com.abdullayev.demoshops.controllers;


import com.abdullayev.demoshops.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import com.abdullayev.demoshops.exceptions.OrderNotFoundException;
import com.abdullayev.demoshops.models.Order;
import com.abdullayev.demoshops.responses.ApiResponse;
import com.abdullayev.demoshops.services.order.IOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
        try {
            OrderDto order = orderService.placeOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Success!", order));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Something went wrong!", e.getMessage()));
        }
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrder(@PathVariable Long orderId) {
        try {
            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Order found!", order));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Something went wrong!", e.getMessage()));
        }
    }

    @GetMapping("/user-orders")
    public ResponseEntity<ApiResponse> getOrdersByUserId(@RequestParam Long userId) {
        try {
            List<OrderDto> orders = orderService.getOrdersByUserId(userId);
            return ResponseEntity.ok(new ApiResponse("success!", orders));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Something went wrong!", e.getMessage()));
        }
    }
}
