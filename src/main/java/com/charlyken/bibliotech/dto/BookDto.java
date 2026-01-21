package com.charlyken.bibliotech.dto;

import com.charlyken.bibliotech.model.Author;
import com.charlyken.bibliotech.model.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.time.LocalDate;
@Data
public class BookDto {
    private Long id;

    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{3}$", message = "l'ISBN doit respecter le format xxx-xxx-xxx")
    private String isbn;

    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 2, max = 60, message = "Le titre est entre 2 et 50 Caractères")
    private String title;

    private LocalDate publicationDate;

    @Min(value = 0, message = "Le Stock ne peut pas être négatif")
    private int availableCopies;

    @Min(value = 0, message = "Le Stock ne peut pas être négatif")
    private int totalCopies;

    private boolean active;
    private Author author;
    private Category category;

    public void setIsbn(String isbnUser) {
        if(isbnUser == null){
            this.isbn = null;
            return;
        }

        //Transformer la chaine en chiffre uniquement
        String chiffrerIsbnUser = isbnUser.replaceAll("[^0-9]","");

        //Appliquer le format xxx-xxx-xxx
        if(chiffrerIsbnUser.length() == 9){
            this.isbn = chiffrerIsbnUser.substring(0, 3) + "-" +
                    chiffrerIsbnUser.substring(3, 6) + "-" +
                    chiffrerIsbnUser.substring(6, 9);
        }else {
            this.isbn = chiffrerIsbnUser;
        }
    }

}
