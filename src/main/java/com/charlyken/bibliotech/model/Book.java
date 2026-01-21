package com.charlyken.bibliotech.model;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "date_de_publication", nullable = false)
    private LocalDate publicationDate;

    @Column(name = "livres_dispo", nullable = false)
    private int availableCopies;

    @Column(name = "livres_total", nullable = false)
    private int totalCopies;

    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY) //Plusieurs livres pour un auteur
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY)// Plusieurs livres pour une catégorie
    @JoinColumn(name = "category_id")
    private Category category;

}
