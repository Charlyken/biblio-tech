package com.charlyken.bibliotech.dataLoader;

import com.charlyken.bibliotech.model.Author;
import com.charlyken.bibliotech.model.Book;
import com.charlyken.bibliotech.model.Category;
import com.charlyken.bibliotech.repository.AuthorRepository;
import com.charlyken.bibliotech.repository.BookRepository;
import com.charlyken.bibliotech.repository.CategoryRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
@RequiredArgsConstructor
@Profile("dev")
@Order(4)
public class BookDataLoader implements CommandLineRunner {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final Faker faker = new Faker(new Locale("fr"));

    @Override
    public void run (String[] args){
        List<Author> authors = authorRepository.findAll();
        List<Category> categories = categoryRepository.findAll();

        if(!authors.isEmpty() && !categories.isEmpty()){
            List<Book> books = new ArrayList<>();

            for (int i = 0; i<20; i++){
                Book book = new Book();

                book.setIsbn(faker.code().isbn13());
                book.setTitle(faker.name().title());
                book.setPublicationDate(LocalDate.of(1900 + i,8, 1));
                book.setTotalCopies(faker.random().nextInt(5, 50));
                int bookTotalCopies = faker.random().nextInt(5, 50);
                book.setAvailableCopies(bookTotalCopies);
                book.setAuthor(authors.get(faker.random().nextInt(authors.size())));
                book.setCategory(categories.get(faker.random().nextInt(categories.size())));

                books.add(book);
            }
            bookRepository.saveAll(books);
        }
        System.out.println("Les livres sont initialisées");
    }

}
