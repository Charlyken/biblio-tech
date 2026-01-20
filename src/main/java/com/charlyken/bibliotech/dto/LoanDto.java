package com.charlyken.bibliotech.dto;

import com.charlyken.bibliotech.model.AppUser;
import com.charlyken.bibliotech.model.Book;
import com.charlyken.bibliotech.model.LoanStatus;
import lombok.Data;
import java.time.LocalDate;

@Data
public class LoanDto {
    private Long id;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private LoanStatus status;
    private boolean active;
    private Book book;
    private AppUser appUser;
}
