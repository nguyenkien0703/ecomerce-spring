package com.ecommerce.shopapp.services.impl;

import com.ecommerce.shopapp.dtos.request.CategoryDTO;
import com.ecommerce.shopapp.entity.Category;
import com.ecommerce.shopapp.repositories.CategoryRepository;
import com.ecommerce.shopapp.services.ICategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;


    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        Category newCategory = Category.builder().
                name(categoryDTO.getName()).build();
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional
    public Category updateCategory(long categoryId,
                                   CategoryDTO categoryDTO) {
        Category existingCategory = getCategoryById(categoryId);
        existingCategory.setName(categoryDTO.getName());
        categoryRepository.save(existingCategory);
        return existingCategory;
    }

    @Override
    @Transactional
    public void deleteCategory(long id)  {
//        Category category = categoryRepository.findById(id)
//                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
//
//        List<Product> products = productRepository.findByCategory(category);
//        if (!products.isEmpty()) {
//            throw new IllegalStateException("Cannot delete category with associated products");
//        } else {
            categoryRepository.deleteById(id);
//            return  category;
//        }
    }
}
