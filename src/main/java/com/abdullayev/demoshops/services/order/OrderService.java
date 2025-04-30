package com.abdullayev.demoshops.services.order;

import com.abdullayev.demoshops.dto.OrderDto;
import com.abdullayev.demoshops.enums.OrderStatus;
import com.abdullayev.demoshops.exceptions.EmptyCartException;
import com.abdullayev.demoshops.exceptions.OrderNotFoundException;
import com.abdullayev.demoshops.models.Cart;
import com.abdullayev.demoshops.models.Order;
import com.abdullayev.demoshops.models.OrderItem;
import com.abdullayev.demoshops.models.Product;
import com.abdullayev.demoshops.repositories.OrderRepository;
import com.abdullayev.demoshops.repositories.ProductRepository;
import com.abdullayev.demoshops.services.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ICartService cartService;
    private final ModelMapper modelMapper;

    @Override
    public OrderDto placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        Set<OrderItem> orderItems = createOrderItems(order, cart);
        if (orderItems.isEmpty()) {
            throw new EmptyCartException("Cannot place order with empty cart");
        }
        order.setItems(orderItems);
        order.setTotalAmount(calculateTotalAmount(orderItems));
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return convertToDto(savedOrder);
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private Set<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getItems().stream()
                .map(cartItem -> {
                    Product product = cartItem.getProduct();
                    product.setInventory(product.getInventory() - cartItem.getQuantity());
                    productRepository.save(product);
                    return new OrderItem(
                            order,
                            product,
                            cartItem.getQuantity(),
                            cartItem.getUnitPrice());
                }).collect(Collectors.toSet());
    }

    private BigDecimal calculateTotalAmount(Set<OrderItem> orderItemList) {
        return orderItemList.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this :: convertToDto)
                .orElseThrow(() -> new OrderNotFoundException("Order not found!"));
    }

    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUser_Id(userId);
        return orders.stream().map(this::convertToDto).toList();
    }

    @Override
    public OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

}
