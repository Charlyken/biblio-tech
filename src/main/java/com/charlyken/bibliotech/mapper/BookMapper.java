package com.charlyken.bibliotech.mapper;

import com.charlyken.bibliotech.dto.BookDto;
import com.charlyken.bibliotech.model.Book;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class BookMapper {

    /**
     * Transforme l'entité Book (Base de données) en BookDto (Vue/API)
     */
    public BookDto mapToDto(Book book) {
        if (book == null) {
            return null;
        }

        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setIsbn(book.getIsbn());
        dto.setTitle(book.getTitle());
        dto.setPublicationDate(book.getPublicationDate());
        dto.setAvailableCopies(book.getAvailableCopies());
        dto.setTotalCopies(book.getTotalCopies());
        dto.setActive(book.isActive());
        
        dto.setAuthor(book.getAuthor());
        dto.setCategory(book.getCategory());

        return dto;
    }

    /**
     * Transforme le BookDto en entité Book pour la persistance
     */
    public Book mapToEntity(BookDto dto) {
        if (dto == null) {
            return null;
        }

        Book book = new Book();
        book.setId(dto.getId());
        book.setIsbn(dto.getIsbn());
        book.setTitle(dto.getTitle());
        book.setPublicationDate(dto.getPublicationDate());
        book.setAvailableCopies(dto.getAvailableCopies());
        book.setTotalCopies(dto.getTotalCopies());
        book.setActive(dto.isActive());
        
        book.setAuthor(dto.getAuthor());
        book.setCategory(dto.getCategory());

        return book;
    }
}
