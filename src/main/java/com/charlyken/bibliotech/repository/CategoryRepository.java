package com.charlyken.bibliotech.repository;

import com.charlyken.bibliotech.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
