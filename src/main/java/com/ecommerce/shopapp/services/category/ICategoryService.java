package com.ecommerce.shopapp.services.category;

import com.ecommerce.shopapp.dtos.request.CategoryDTO;
import com.ecommerce.shopapp.entity.Category;

import java.util.List;

public interface ICategoryService {

    Category createCategory(CategoryDTO categoryDTO);

    Category getCategoryById(long id);

    List<Category> getAllCategories();

    Category updateCategory(long categoryId, CategoryDTO categoryDTO);

    Category deleteCategory(long id) throws Exception;




}
