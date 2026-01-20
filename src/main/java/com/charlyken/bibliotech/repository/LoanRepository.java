package com.charlyken.bibliotech.repository;

import com.charlyken.bibliotech.model.AppUser;
import com.charlyken.bibliotech.model.Loan;
import com.charlyken.bibliotech.model.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByUserId(Long userId);

    List<Loan> findByStatus(LoanStatus status);

    Optional<Loan> findByUserAndStatus(AppUser user, LoanStatus status);

    long countByUserIdAndStatus(Long userId, LoanStatus status);

    long countLoanByUser(AppUser user);
    
    List<Loan> findByDueDateBeforeAndReturnDateIsNull(LocalDate today);
}
