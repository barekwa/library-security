package com.example.project.mapper;

import com.example.project.dto.loan.LoanDTO;
import com.example.project.model.Loan;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

@UtilityClass
public class LoanMapper {
    public Loan loanDTOToLoan(LoanDTO loanDTO){
        Loan loan = new Loan();
        BeanUtils.copyProperties(loanDTO, loan);
        return loan;
    }

    public LoanDTO loanToLoanDTO(Loan loan){
        LoanDTO loanDTO = new LoanDTO();
        BeanUtils.copyProperties(loan, loanDTO);
        return loanDTO;
    }
}
