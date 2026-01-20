package com.charlyken.bibliotech.controller;


import com.charlyken.bibliotech.dto.BookDto;
import com.charlyken.bibliotech.exception.BusinessException;
import com.charlyken.bibliotech.model.Book;
import com.charlyken.bibliotech.service.AuthorService;
import com.charlyken.bibliotech.service.BookService;
import com.charlyken.bibliotech.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/admin/books")
public class BookController {
    private BookService bookService;
    private AuthorService authorService;
    private CategoryService categoryService;

    /**
     * Affichage de la liste des livres
     */
    @GetMapping("")
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "admin/book/books";
    }

    /**
     * Dirige vers le formulaire de Modification des books
     */
    @GetMapping("/edit/{id}")
    public String showEditBookForm(@PathVariable Long id, Model model) {
        log.info("Editing book {}", id);
        //1. Attribution du livre au model
        model.addAttribute("book", bookService.findBookById(id));
        //2. Recuperation de la liste des auteurs presents
        model.addAttribute("authors", authorService.getAllAuthors());
        //3. Recuperation de la liste des categories presentes
        model.addAttribute("categories", categoryService.getAllCategories());

        return "admin/book/book-form";
    }

    /**
     * Sauvegarde des informations issues du formulaire
     */
    @PostMapping("/save")
    public String saveBook(@Valid @ModelAttribute("book") BookDto bookDto, BindingResult result,
                           @RequestParam(required = false) Long authorId,
                           @RequestParam(required = false) Long categoryId, Model model) {
        //@ModelAttribute prend tout les champs du formulaire
        // et les assemble dans un objet Book
        // Si book.id est null alors c'est un nouveau book
        // Si book.id !=null alors c'est MODIF


        log.info("Book to edit: {} with authorId: {} and categoryId: {}", bookDto, authorId, categoryId);

        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/book/book-form";
        }

        try {
            bookService.bookSave(authorId, categoryId, bookDto);
            return "redirect:/admin/books";
        } catch (BusinessException e) {
            model.addAttribute("registrationError",e.getMessage());
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/book/book-form";

        }

    }

    /**
     * Dirige vers le formulaire d'ajout pour un nouveau book
     */
    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new BookDto());
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/book/book-form";
    }

    @GetMapping("/delete/{id}")
    public  String deleteBook(@PathVariable Long id){
        bookService.bookDeleteById(id);
        return "redirect:/admin/books";
    }

}
