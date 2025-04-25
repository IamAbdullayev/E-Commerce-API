package com.abdullayev.demoshops.repositories;

import com.abdullayev.demoshops.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long id);

    Set<CartItem> findByCart_Id(Long cartId);
}

