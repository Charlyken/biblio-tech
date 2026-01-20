package com.charlyken.bibliotech.controller;

import com.charlyken.bibliotech.dto.BookDto;
import com.charlyken.bibliotech.dto.LoanDto;
import com.charlyken.bibliotech.exception.BusinessException;
import com.charlyken.bibliotech.service.BookService;
import com.charlyken.bibliotech.service.LoanService;
import com.charlyken.bibliotech.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final LoanService loanService;
    private final BookService bookService;

    /**
     * Affiche le tableau de bord de l'utilisateur avec ses emprunts et ses livres
     */
    @GetMapping("/dashboard")
    public String showUserDashboard (Model model, Principal principal){
        String username = principal.getName();
        log.debug("Accès au tableau de bord pour l'utilisateur : {}", username);

        Long userId = userService.findIdByUsername(username);

        //Récupération des données
        List<LoanDto> userLoans = loanService.getUserLoansById(userId);
        List<BookDto> userBooks = bookService.getAllBooks();

        // Envoie à la vue
        model.addAttribute("myLoans", userLoans);
        model.addAttribute("books", userBooks);

        log.info("Dashboard chargé pour {} : {} emprunts, {} livres.", username, userLoans.size(), userBooks.size());

        return "user/dashboard";
    }

    @PostMapping("/loans/create")
    public String createLoan (@RequestParam Long bookId, Model model, RedirectAttributes redirectAttributes, Principal principal){
        try{
            String username = principal.getName();
            log.info("Username {} , bookId {}", username, bookId);
            loanService.createLoan(username, bookId);
            redirectAttributes.addFlashAttribute("success", "Emprunt validé");
            return "redirect:/user/dashboard";
        } catch (BusinessException e) {
            model.addAttribute("error", e.getMessage());
            return "user/dashboard";
        }
    }

    @GetMapping("/loans/close/{loanId}")
    public String closeLoan(@PathVariable Long loanId, RedirectAttributes redirectAttributes){
        loanService.closeLoan(loanId);
        redirectAttributes.addFlashAttribute("success", "Le livre à été rendu");
        return "redirect:/user/dashboard";
    }

}
