package com.springcodework.dreamcart.repository;

import com.springcodework.dreamcart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom query methods
    boolean existsByEmail(String email);  // For checking if a user already exists by email
}
