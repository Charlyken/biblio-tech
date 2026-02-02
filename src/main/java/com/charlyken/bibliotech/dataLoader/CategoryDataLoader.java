package com.charlyken.bibliotech.dataLoader;

import com.charlyken.bibliotech.model.Category;
import com.charlyken.bibliotech.repository.CategoryRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
@Component
@RequiredArgsConstructor
@Profile("dev")
@Order(2)
public class CategoryDataLoader implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final Faker faker = new Faker(new Locale("fr"));

    @Override
    public void run (String[] args){
        if(categoryRepository.count() == 0){
            List<Category> categories = new ArrayList<>();

            for(int i=0; i<10; i++){
                Category category = new Category();

                category.setName(faker.book().genre());
                categories.add(category);
            }
            categoryRepository.saveAll(categories);
        }
        System.out.println("Les categories sont initialisées");
    }

}
