package com.abdullayev.demoshops.services.category;

import com.abdullayev.demoshops.models.Category;
import com.abdullayev.demoshops.requests.category.AddCategoryRequest;
import com.abdullayev.demoshops.requests.category.UpdateCategoryRequest;

import java.util.List;

public interface ICategoryService {
    Category addCategory(AddCategoryRequest request);
    Category updateCategory(UpdateCategoryRequest request, Long categoryId);
    void deleteCategory(Long id);
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
}
