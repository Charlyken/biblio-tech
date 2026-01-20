package com.charlyken.bibliotech.service;

import com.charlyken.bibliotech.model.Category;
import com.charlyken.bibliotech.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

}
