package com.abdullayev.demoshops.services.cart;

import com.abdullayev.demoshops.dto.CartDto;
import com.abdullayev.demoshops.models.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initializeNewCart();

    CartDto covertToDto(Cart cart);
}
