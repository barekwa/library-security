package com.example.project.api.service.interfaces;

import com.example.project.dto.loan.LoanDTO;
import com.example.project.dto.loan.LoanEditDTO;

import java.util.List;

public interface LoanServiceAPI {

    List<LoanDTO> getLoansByUserName(String username);

    LoanDTO loanBook(LoanDTO loan);

    LoanEditDTO returnBook(LoanEditDTO loan);
}
