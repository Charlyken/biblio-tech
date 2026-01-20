package com.charlyken.bibliotech.service;

import com.charlyken.bibliotech.dto.BookDto;
import com.charlyken.bibliotech.dto.LoanDto;
import com.charlyken.bibliotech.exception.BookRuleException;
import com.charlyken.bibliotech.mapper.BookMapper;
import com.charlyken.bibliotech.model.Author;
import com.charlyken.bibliotech.model.Book;
import com.charlyken.bibliotech.model.Category;
import com.charlyken.bibliotech.repository.AuthorRepository;
import com.charlyken.bibliotech.repository.BookRepository;

import com.charlyken.bibliotech.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final LoanService loanService;
    private final BookMapper bookMapper;

    @Transactional
    public void bookSave(Long idAuthor, Long idCategory, BookDto bookDto) {
        // 1. Validation du titre
        //Règle metier : le titre est unique
        bookRepository.findByTitle(bookDto.getTitle()).ifPresent(existingBook -> {
            if (bookDto.getId() == null || !existingBook.getId().equals(bookDto.getId())) {
                throw new BookRuleException("Ce titre est déjà utilisé par un autre livre.");
            }
        });

        Book bookToSave;

        // 2. Distinction CREATION vs EDITION
        if (bookDto.getId() != null) {
            // --- EDITION ---
            bookToSave = bookRepository.findById(bookDto.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Livre introuvable"));

            // Mise à jour des champs simples
            bookToSave.setTitle(bookDto.getTitle());
            bookToSave.setIsbn(bookDto.getIsbn());
            bookToSave.setPublicationDate(bookDto.getPublicationDate());
            bookToSave.setAvailableCopies(bookDto.getAvailableCopies());
            bookToSave.setTotalCopies(bookDto.getTotalCopies());
            bookToSave.setActive(bookDto.isActive());
        } else {
            // --- CREATION ---
            bookToSave = bookMapper.mapToEntity(bookDto);
        }

        // 3. Gestion des relations
        if (idAuthor != null) {
            Author author = findAuthorsById(idAuthor);
            bookToSave.setAuthor(author);
        }
        if (idCategory != null) {
            Category category = findCategoryById(idCategory);
            bookToSave.setCategory(category);
        }

        // 4. Sauvegarde
        bookRepository.save(bookToSave);
    }

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<BookDto> getUserBooksById(Long userId) {
        List<LoanDto> userLoans = loanService.getUserLoansById(userId);

        return userLoans.stream()
                .map(LoanDto::getBook)
                .distinct()
                .map(bookMapper::mapToDto)
                .collect(Collectors.toList());

    }

    public BookDto findBookById(Long idBook) {
        Book book = bookRepository.findById(idBook)
                .orElseThrow(() -> new EntityNotFoundException("Livre non trouvé !"));

        return bookMapper.mapToDto(book);
    }

    public void bookDeleteById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Livre non trouvé !"));

        book.setActive(false);
        bookRepository.save(book);
    }

    public long totalBooks() {
        return bookRepository.count();
    }

    //--- Méthodes internes ---

    private Author findAuthorsById(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Auteur introuvable, ID : " + authorId));
    }

    private Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Catégorie introuvable, ID : " + categoryId));
    }

    

}
