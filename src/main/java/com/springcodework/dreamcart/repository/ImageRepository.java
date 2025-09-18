package com.springcodework.dreamcart.repository;

import com.springcodework.dreamcart.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {
    List<Image> findByProductId(Long productId);
}
