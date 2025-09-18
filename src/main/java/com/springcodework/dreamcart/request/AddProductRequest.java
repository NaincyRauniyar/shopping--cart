package com.springcodework.dreamcart.request;
import com.springcodework.dreamcart.model.Category; // ✅ must be this one

import com.springcodework.dreamcart.model.Category;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

import static jakarta.persistence.CascadeType.ALL;
@Data
public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
    private String categoryName;
}
