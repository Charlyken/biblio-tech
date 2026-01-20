package com.charlyken.bibliotech;

import com.charlyken.bibliotech.model.AppUser;
import com.charlyken.bibliotech.model.Author;
import com.charlyken.bibliotech.model.Book;
import com.charlyken.bibliotech.model.Category;
import com.charlyken.bibliotech.repository.AppUserRepository;
import com.charlyken.bibliotech.repository.AuthorRepository;
import com.charlyken.bibliotech.repository.BookRepository;
import com.charlyken.bibliotech.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.beans.Encoder;
import java.time.LocalDate;

@SpringBootApplication
public class BiblioTechApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiblioTechApplication.class, args);
    }


}
