package com.ecommerce.shopapp.repositories;

import com.ecommerce.shopapp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
