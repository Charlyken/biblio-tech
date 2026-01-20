package com.charlyken.bibliotech.service;

import com.charlyken.bibliotech.model.Author;
import com.charlyken.bibliotech.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor

@Service
public class AuthorService {
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }
}
