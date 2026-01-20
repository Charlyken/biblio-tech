package com.charlyken.bibliotech.controller;


import com.charlyken.bibliotech.service.BookService;
import com.charlyken.bibliotech.service.LoanService;
import com.charlyken.bibliotech.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")

public class AdminController {
    private final UserService userService;
    private final LoanService loanService;
    private final BookService bookService;

    @GetMapping("/dashboard")
    public String DashBoard(Model model){
        model.addAttribute("totalBooks", bookService.totalBooks());
        model.addAttribute("activeLoans", loanService.totalLoans());
        model.addAttribute("totalUsers", userService.totalUsers() - 1L);

        return "admin/dashboard";
    }
}
