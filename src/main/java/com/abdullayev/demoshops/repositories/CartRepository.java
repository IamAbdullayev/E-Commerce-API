package com.abdullayev.demoshops.repositories;

import com.abdullayev.demoshops.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
