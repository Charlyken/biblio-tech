package com.charlyken.bibliotech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{3}$", message = "l'ISBN doit respecter le format xxx-xxx-xxx")
    @Column(name = "isbn", nullable = false)
    private String isbn;

    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 2, max = 60, message = "Le titre est entre 2 et 50 Caractères")
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "date_de_publication", nullable = false)
    private LocalDate publicationDate;

    @Min(value = 0, message = "Le Stock ne peut pas être négatif")
    @Column(name = "livres_dispo", nullable = false)
    private int availableCopies;

    @Min(value = 1, message = "Le Stock ne peut pas être négatif")
    @Column(name = "livres_total", nullable = false)
    private int totalCopies;

    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY) //Plusieurs livres pour un auteur
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY)// Plusieurs livres pour une catégorie
    @JoinColumn(name = "category_id")
    private Category category;

    public void setIsbn(String isbnUser) {
        //1. entreé du user null
        if(isbnUser == null){
            this.isbn = isbnUser;
            return;
        }

        //2. transformer la chaine en chiffre uniquement
        String chiffrerIsbnUser = isbnUser.replaceAll("[^0-9]","");

        //3. Appliquer le format xxx-xxx-xxx
        if(chiffrerIsbnUser.length() == 9){
            this.isbn = chiffrerIsbnUser.substring(0, 3) + "-" +
                    chiffrerIsbnUser.substring(3, 6) + "-" +
                    chiffrerIsbnUser.substring(6, 9);
        }else {
            this.isbn = chiffrerIsbnUser;
        }
    }
}
