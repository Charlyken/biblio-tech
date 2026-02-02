package com.charlyken.bibliotech.dataLoader;

import com.charlyken.bibliotech.model.AppUser;
import com.charlyken.bibliotech.model.Book;
import com.charlyken.bibliotech.model.Loan;
import com.charlyken.bibliotech.model.LoanStatus;
import com.charlyken.bibliotech.repository.AppUserRepository;
import com.charlyken.bibliotech.repository.BookRepository;
import com.charlyken.bibliotech.repository.LoanRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@Profile("dev")
@Order(5)
public class LoanDataLoader implements CommandLineRunner {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final AppUserRepository appUserRepository;
    private final Faker faker = new Faker(new Locale("fr"));

    @Override
    public void run (String ...args){
        List<AppUser> appUsers = appUserRepository.findAll();
        List<Book> books = bookRepository.findAll();
        if(!appUsers.isEmpty() && !books.isEmpty()){
            List<Loan> loans = new ArrayList<>();

            for (int i = 0; i<9; i++){
                Loan loan = new Loan();

                loan.setBook(books.get(i));
                loan.setUser(appUsers.get(i));
                loan.setLoanDate(LocalDate.now().minusDays(5)); // Emprunté il y a 5 jours
                loan.setDueDate(LocalDate.now().plusDays(10));  // À rendre dans 10 jours
                loan.setStatus(LoanStatus.EN_COURS);

                loans.add(loan);
            }
            loanRepository.saveAll(loans);
        }
        System.out.println("Les Emprunts sont initialisés");
    }
}
