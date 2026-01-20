package com.charlyken.bibliotech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    private boolean active = true;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL) //Un auteur à plusieurs livres
    @ToString.Exclude //Exclusion de la liste de livres de la methode toString()
    private List<Book> bookList = new ArrayList<>();

}
