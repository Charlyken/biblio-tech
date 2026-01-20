package com.charlyken.bibliotech.controller;

import com.charlyken.bibliotech.exception.BusinessException;
import com.charlyken.bibliotech.model.Loan;
import com.charlyken.bibliotech.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
@RequestMapping("/admin/loans")

public class LoanController {
    private LoanService loanService;

    @GetMapping("")
    public String listLoans (Model model) {
        model.addAttribute("loans",loanService.getAllLoans());
        return "admin/loans";
    }

    @PostMapping("/create")
    public String createLoan (@RequestParam Long bookId, Authentication authentication, RedirectAttributes redirectAttributes){
       try{
           //1. Username de la session
           String username = authentication.getName();

           loanService.createLoan(username, bookId);

           redirectAttributes.addFlashAttribute("success","Livre emprunté avec succès !");
       } catch (BusinessException e) {
           redirectAttributes.addFlashAttribute("error",e.getMessage());
       }

       return "redirect:/admin/books";

    }

    @GetMapping("/close/{loanId}")
    public String closeLoan(@PathVariable Long loanId, RedirectAttributes redirectAttributes){
      try{
          loanService.closeLoan(loanId);

          redirectAttributes.addFlashAttribute("popupMessage","Le livre à été rendu");
      } catch (RuntimeException e) {
          redirectAttributes.addFlashAttribute("error",e.getMessage());
      }

        return "redirect:/loans";
    }
}
