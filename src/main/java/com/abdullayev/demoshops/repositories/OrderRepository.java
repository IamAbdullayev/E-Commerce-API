package com.abdullayev.demoshops.repositories;

import com.abdullayev.demoshops.models.Order;
import com.abdullayev.demoshops.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_Id(Long userId);

    Long user(User user);
}
