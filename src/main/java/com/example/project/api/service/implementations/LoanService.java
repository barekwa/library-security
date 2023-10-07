package com.example.project.api.service.implementations;

import com.example.project.dto.loan.LoanDTO;
import com.example.project.dto.loan.LoanEditDTO;
import com.example.project.exception.LoanException;
import com.example.project.mapper.LoanMapper;
import com.example.project.model.Book;
import com.example.project.model.Loan;
import com.example.project.model.User;
import com.example.project.api.repository.BookRepository;
import com.example.project.api.repository.LoanRepository;
import com.example.project.api.repository.UserRepository;
import com.example.project.api.service.interfaces.LoanServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService implements LoanServiceAPI {
    private final LoanRepository loanRepository;

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<LoanDTO> getLoansByUserName(String username) {

        return loanRepository.findByUserName(username).stream()
                .map(LoanMapper::loanToLoanDTO)
                .toList();
    }

    @Override
    public LoanDTO loanBook(LoanDTO loan) {
        if (loan == null)
            throw new LoanException("Invalid loan model");

        Optional<Book> book = bookRepository.findById(loan.getBookId());
        Optional<User> user = userRepository.findById(loan.getUserId());

        if (book.isPresent() && user.isPresent() && (book.get().getQuantity() > 0)) {

            //updating quantity of avaivable books after loan
            book.get().setQuantity(book.get().getQuantity() - 1);
            bookRepository.save(book.get());

            Loan loanToSave;
            loanToSave = LoanMapper.loanDTOToLoan(loan);
            loanToSave.setBook(book.get());
            loanToSave.setUser(user.get());

            loanRepository.save(loanToSave);
            return loan;

        }
        throw new LoanException("Cannot loan book");
    }

    @Override
    public LoanEditDTO returnBook(LoanEditDTO loan) {

        if (loan == null)
            throw new LoanException("Invalid loan model");

        Optional<Loan> loanToUpdate = loanRepository.findById(loan.getId());
        Optional<Book> book = bookRepository.findById(loan.getBookId());
        Optional<User> user = userRepository.findById(loan.getUserId());

        if (book.isPresent() && loanToUpdate.isPresent() && user.isPresent()) {

            //updating quantity of avaivable books after return
            book.get().setQuantity(book.get().getQuantity() + 1);
            bookRepository.save(book.get());

            loanToUpdate.get().setReturned(true);
            loanRepository.save(loanToUpdate.get());
            return loan;
        } else
            throw new LoanException("Cannot return book");
    }
}
