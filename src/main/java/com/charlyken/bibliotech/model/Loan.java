package com.charlyken.bibliotech.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name = "emprunts")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_emprunt", nullable = false)
    private LocalDate loanDate;

    @Column(name = "date_limite", nullable = false)
    private LocalDate dueDate;

    @Column(name = "date_retour")
    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private LoanStatus status;

    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY) //Un livre peut apparaitre dans plusieurs emprunts.
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)//Un user peut avoir plusieurs emprunts
    @JoinColumn(name = "user_id")
    private AppUser user;

}
