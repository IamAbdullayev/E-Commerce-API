package com.abdullayev.demoshops.services.cart;

import com.abdullayev.demoshops.dto.CartDto;
import com.abdullayev.demoshops.models.Cart;
import com.abdullayev.demoshops.models.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(User user);

    CartDto convertToDto(Cart cart);

    Cart getCartByUserId(Long userId);
}
