package com.charlyken.bibliotech.repository;

import com.charlyken.bibliotech.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
