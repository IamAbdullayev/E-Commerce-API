package com.abdullayev.demoshops.services.cart;

import com.abdullayev.demoshops.models.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cardId, Long productId, Integer quantity);
    void removeItemFromCart(Long cardId, Long productId);
    void updateItemQuantity(Long cardId, Long productId, Integer quantity);
    CartItem getCartItem(Long cartId, Long productId);
}
