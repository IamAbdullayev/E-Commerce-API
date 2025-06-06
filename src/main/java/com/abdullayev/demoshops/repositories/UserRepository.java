package com.abdullayev.demoshops.repositories;

import com.abdullayev.demoshops.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    User findByEmail(String username);
}
