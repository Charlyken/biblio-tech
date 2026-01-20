package com.charlyken.bibliotech.mapper;

import com.charlyken.bibliotech.dto.LoanDto;
import com.charlyken.bibliotech.model.Loan;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class LoanMapper {
    public LoanDto mapToDto(Loan loan) {
        if (loan == null){
            return null;
        }

        LoanDto loanDto = new LoanDto();
        loanDto.setId(loan.getId());
        loanDto.setLoanDate(loan.getLoanDate());
        loanDto.setDueDate(loan.getDueDate());
        loanDto.setReturnDate(loan.getReturnDate());
        loanDto.setStatus(loan.getStatus());
        loanDto.setActive(loan.isActive());
        loanDto.setBook(loan.getBook());
        loanDto.setAppUser(loan.getUser());
        return loanDto;
    }
}
