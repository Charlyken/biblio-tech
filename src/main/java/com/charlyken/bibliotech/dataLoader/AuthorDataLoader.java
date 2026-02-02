package com.charlyken.bibliotech.dataLoader;

import com.charlyken.bibliotech.model.Author;
import com.charlyken.bibliotech.repository.AuthorRepository;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Ce composant est responsable du seeding des auteurs dans la base de données.
 * Il ne s'exécute que si le profil 'dev' est actif.
 * L'annotation @Order(1) garantit qu'il s'exécute en premier.
 */
@Component
@RequiredArgsConstructor
@Profile("dev")
@Order(1)
public class AuthorDataLoader implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final Faker faker = new Faker(new Locale("fr"));

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if (authorRepository.count() == 0) {
            List<Author> authors = new ArrayList<>();
           for (int i=0; i<10;i++) {
               Author author = new Author();

               author.setName(faker.name().fullName());
               authors.add(author);
           }
           authorRepository.saveAll(authors);
            System.out.println("Auteurs creés avec succès!");
        }
    }
}
