package com.charlyken.bibliotech.service;

import java.util.List;
import com.charlyken.bibliotech.dto.LoanDto;
import com.charlyken.bibliotech.exception.BookRuleException;
import com.charlyken.bibliotech.exception.LoanNotFoundException;
import com.charlyken.bibliotech.exception.LoanRuleException;
import com.charlyken.bibliotech.mapper.LoanMapper;
import com.charlyken.bibliotech.model.AppUser;
import com.charlyken.bibliotech.model.Book;
import com.charlyken.bibliotech.model.Loan;
import com.charlyken.bibliotech.model.LoanStatus;
import com.charlyken.bibliotech.repository.AppUserRepository;
import com.charlyken.bibliotech.repository.BookRepository;
import com.charlyken.bibliotech.repository.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * Service qui gérant la logique métier des emprunts
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoanService {

    private static final int DEFAULT_LOAN_DAYS = 14;
    private final LoanRepository loanRepository;
    private final AppUserRepository appUserRepository;
    private final BookRepository bookRepository;
    private final LoanMapper loanMapper;

    @Transactional
    public Loan createLoan(String username, Long bookId){
        AppUser user = findUserByUsername(username);
        Book book = findBookById(bookId);

        validateLoanEligibility(user, book);

        Loan loan = initializeNewLoan(user, book);
        decrementBookAvailability(book);

        log.info("Emprunt créé avec succès : User={}, Book={}", username, bookId);
        return loanRepository.save(loan);
    }

    @Transactional
    public Loan closeLoan(Long loanId){
       Loan loan = findLoanById(loanId);

       validateLoanIsActive(loan);

       terminateLoan(loan);
       incrementBookAvailability(loan.getBook());

       log.info("Emprunt cloturé avec succès : ID={}", loanId);
       return loanRepository.save(loan);


    }

    public List<LoanDto> getAllLoans(){
        List <Loan> allLoans = loanRepository.findAll();
        return allLoans.stream()
                .map(loanMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<LoanDto> getUserLoansById(Long userId){
        List<Loan> loans = loanRepository.findByUserId(userId);

        return loans.stream()
                .map(loanMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public long totalLoans (){
        return loanRepository.count();
    }


    //--- Méthodes IMPORTANTES ---

    private AppUser findUserByUsername(String username){
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable : " + username));
    }

    private Book findBookById(Long bookId){
        return bookRepository.findByIdWithLock(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Livre introuvable ID : " + bookId));
    }

    private Loan findLoanById (Long loanId){
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Emprunt introuvable ID : " + loanId));
    }

    private void validateLoanEligibility(AppUser user, Book book){
        if(book.getAvailableCopies() == 0){
            throw new BookRuleException("Le livre n'est plus en stock.");
        }

        boolean hasLateLoan = loanRepository.findByUserAndStatus(user, LoanStatus.EN_RETARD).isPresent();
        if(hasLateLoan){
            throw new LoanRuleException("L'utilisateur " + user.getUsername() + " a un emprunt en retard.");
        }

        long userLoanSize = loanRepository.countLoanByUser(user);
        if(userLoanSize >= 3){
            throw new LoanRuleException("L'utilisateur " + user.getUsername() + " à dejà " + userLoanSize + " emprunts");
        }
    }

    private void validateLoanIsActive(Loan loan){
        if(!LoanStatus.EN_COURS.equals(loan.getStatus())){
            throw new LoanRuleException("Cet emprunt est dejà terminé ou annulé");
        }
    }

    private Loan initializeNewLoan(AppUser user, Book book) {
        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(DEFAULT_LOAN_DAYS));
        loan.setStatus(LoanStatus.EN_COURS);
        loan.setActive(true);
        return loan;
    }

    private void terminateLoan(Loan loan) {
        loan.setStatus(LoanStatus.TERMINE);
        loan.setReturnDate(LocalDate.now());
        loan.setActive(false);
    }

    private void decrementBookAvailability(Book book) {
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);
    }

    private void incrementBookAvailability(Book book) {
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);
    }

}
