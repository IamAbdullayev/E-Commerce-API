package com.abdullayev.demoshops.services.category;

import com.abdullayev.demoshops.exceptions.AlreadyExistsException;
import com.abdullayev.demoshops.exceptions.CategoryNotFoundException;
import com.abdullayev.demoshops.exceptions.InvalidCategoryNameException;
import com.abdullayev.demoshops.exceptions.ProductNotFoundException;
import com.abdullayev.demoshops.models.Category;
import com.abdullayev.demoshops.repositories.CategoryRepository;
import com.abdullayev.demoshops.requests.category.AddCategoryRequest;
import com.abdullayev.demoshops.requests.category.UpdateCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category addCategory(AddCategoryRequest request) {
        return Optional.of(request).filter(r -> !categoryRepository.existsByName(r.getName()))
                .map(this :: createCategory)
                .map(categoryRepository :: save)
                .orElseThrow(() -> new AlreadyExistsException(request.getName() + " already exist!"));

//        if (categoryRepository.existsByName(request.getName())) {
//            throw new AlreadyExistsException(request.getName() + " already exist!");
//        }
//
//        return categoryRepository.save(createCategory(request));
    }

    private Category createCategory(AddCategoryRequest request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new InvalidCategoryNameException("Category name must not be empty");
        }
        return new Category(request.getName().trim());
    }

    @Override
    public Category updateCategory(UpdateCategoryRequest request, Long categoryId) {
        Category existingCategory = getCategoryById(categoryId);
        if (request.getName() == null || request.getName().isBlank()) {
            throw new InvalidCategoryNameException("Category name must not be empty");
        }
        existingCategory.setName(request.getName());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id).ifPresentOrElse(
                categoryRepository::delete,
                () -> { throw new ProductNotFoundException("Category not found!"); }
        );
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()-> new CategoryNotFoundException("Category not found!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }
}
