package com.charlyken.bibliotech.dataLoader;

import com.charlyken.bibliotech.model.*;
import com.charlyken.bibliotech.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder; // Assure-toi d'avoir Spring Security
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    // Injection de tous les repositories nécessaires
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AppUserRepository appUserRepository;
    private final LoanRepository loanRepository;

    // Pour encoder les mots de passe
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // On vérifie si la base est vide
        if (categoryRepository.count() == 0) {
            System.out.println("Initialisation des données de test");
            initData();
            System.out.println("Données chargées avec succès !");
        }
    }

    private void initData() {
        // 1. CRÉATION DES CATÉGORIES
        Category roman = new Category();
        roman.setName("Roman");
        categoryRepository.save(roman);

        Category scifi = new Category();
        scifi.setName("Science-Fiction");
        categoryRepository.save(scifi);

        Category tech = new Category();
        tech.setName("Informatique");
        categoryRepository.save(tech);

        // 2. CRÉATION DES AUTEURS
        Author hugo = new Author();
        hugo.setName("Victor Hugo");
        authorRepository.save(hugo);

        Author asimov = new Author();
        asimov.setName("Isaac Asimov");
        authorRepository.save(asimov);

        Author uncleBob = new Author();
        uncleBob.setName("Robert C. Martin");
        authorRepository.save(uncleBob);

        //3. CRÉATION DES UTILISATEURS
        // Admin
        AppUser admin = new AppUser();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123")); // Toujours encoder !
        admin.setRole("ADMIN");
        appUserRepository.save(admin);

        // User standard
        AppUser userJean = new AppUser();
        userJean.setUsername("jean");
        userJean.setPassword(passwordEncoder.encode("jean123"));
        userJean.setRole("USER");
        appUserRepository.save(userJean);

        // 4. CRÉATION DES LIVRES

        // Livre 1 : Les Misérables (Stock dispo)
        Book miserables = new Book();
        miserables.setTitle("Les Misérables");
        // le setter va transformer ça en "111-222-333"
        miserables.setIsbn("111222333");
        miserables.setPublicationDate(LocalDate.of(1862, 1, 1));
        miserables.setTotalCopies(5);
        miserables.setAvailableCopies(4); // 1 emprunté plus bas
        miserables.setAuthor(hugo);
        miserables.setCategory(roman);
        bookRepository.save(miserables);

        // Livre 2 : Foundation (Stock épuisé pour tester le bouton "disabled")
        Book foundation = new Book();
        foundation.setTitle("Foundation");
        foundation.setIsbn("999888777");
        foundation.setPublicationDate(LocalDate.of(1951, 6, 1));
        foundation.setTotalCopies(2);
        foundation.setAvailableCopies(0); // Plus de stock !
        foundation.setAuthor(asimov);
        foundation.setCategory(scifi);
        bookRepository.save(foundation);

        // Livre 3 : Clean Code (Tout neuf)
        Book cleanCode = new Book();
        cleanCode.setTitle("Clean Code");
        cleanCode.setIsbn("555444333");
        cleanCode.setPublicationDate(LocalDate.of(2008, 8, 1));
        cleanCode.setTotalCopies(10);
        cleanCode.setAvailableCopies(10);
        cleanCode.setAuthor(uncleBob);
        cleanCode.setCategory(tech);
        bookRepository.save(cleanCode);

        // --- 5. CRÉATION DES EMPRUNTS (LOANS) ---

        // Cas A : Emprunt EN_COURS (Jean a emprunté Les Misérables)
        Loan loan1 = new Loan();
        loan1.setBook(miserables);
        loan1.setUser(userJean);
        loan1.setLoanDate(LocalDate.now().minusDays(5)); // Emprunté il y a 5 jours
        loan1.setDueDate(LocalDate.now().plusDays(10));  // À rendre dans 10 jours
        loan1.setStatus(LoanStatus.EN_COURS);
        loanRepository.save(loan1);

       /* // Cas B : Emprunt EN_RETARD (Jean n'a pas rendu Foundation)
        Loan loan2 = new Loan();
        loan2.setBook(foundation);
        loan2.setUser(userJean);
        loan2.setLoanDate(LocalDate.now().minusDays(20));
        loan2.setDueDate(LocalDate.now().minusDays(5)); // La date limite est passée !
        loan2.setStatus(LoanStatus.EN_RETARD);
        loanRepository.save(loan2);

        // Cas C : Emprunt TERMINE (Jean a rendu un autre Foundation)
        Loan loan3 = new Loan();
        loan3.setBook(foundation);
        loan3.setUser(userJean);
        loan3.setLoanDate(LocalDate.now().minusMonths(1));
        loan3.setDueDate(LocalDate.now().minusDays(15));
        loan3.setReturnDate(LocalDate.now().minusDays(14)); // Rendu à temps
        loan3.setStatus(LoanStatus.TERMINE);
        loanRepository.save(loan3);

        */
    }
}