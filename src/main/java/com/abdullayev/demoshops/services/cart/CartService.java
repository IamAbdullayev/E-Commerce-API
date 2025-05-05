package com.abdullayev.demoshops.services.cart;

import com.abdullayev.demoshops.dto.CartDto;
import com.abdullayev.demoshops.exceptions.CartNotFoundException;
import com.abdullayev.demoshops.models.Cart;
import com.abdullayev.demoshops.models.User;
import com.abdullayev.demoshops.repositories.CartItemRepository;
import com.abdullayev.demoshops.repositories.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ModelMapper modelMapper;

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException("Cart not found!"));
        BigDecimal totalAmount = cart.getTotalAmount();   // ??????????????
        cart.setTotalAmount(totalAmount);                 // ??????????????
        return cartRepository.save(cart);                 // ??????????????
    }

    /*    @Override
    public Cart getCart(Long userId) {
        return Optional.ofNullable(getCartByUserId(userId))
                .map(cart -> {
                    // If the cart exists, return it
                    BigDecimal totalAmount = cart.getTotalAmount();
                    cart.setTotalAmount(totalAmount);
                    return cartRepository.save(cart);
                })
                .orElseGet(() -> {
                    // If the cart does not exist, initialize a new cart
                    User user = Optional.ofNullable(userService.getUserById(userId))
                            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
                    return initializeNewCart(user);
                });
    }*/

    @Override
    public Cart initializeNewCart(User user) {
        return Optional.ofNullable(getCartByUserId(user.getId()))
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
//        cart.getUser().setCart(null); // If we want to delete cart from database
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUser_Id(userId);
    }

    @Override
    public CartDto convertToDto(Cart cart) {
        return modelMapper.map(cart, CartDto.class);
    }

}
