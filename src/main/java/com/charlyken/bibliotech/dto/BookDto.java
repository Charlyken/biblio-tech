package com.charlyken.bibliotech.dto;

import com.charlyken.bibliotech.model.Author;
import com.charlyken.bibliotech.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long id;
    private String isbn;
    private String title;
    private LocalDate publicationDate;
    private int availableCopies;
    private int totalCopies;
    private boolean active;
    private Author author;
    private Category category;

}
