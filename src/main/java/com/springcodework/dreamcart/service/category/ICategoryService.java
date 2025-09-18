package com.springcodework.dreamcart.service.category;

import com.springcodework.dreamcart.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory (Category category);
    Category updateCategory(Category category,Long id);
    Category deleteCategoryById(Long id);
}
