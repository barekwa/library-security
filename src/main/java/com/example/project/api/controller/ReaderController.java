package com.example.project.api.controller;

import com.example.project.dto.book.BookDetailsDTO;
import com.example.project.dto.book.BookReaderDTO;
import com.example.project.dto.loan.LoanDTO;
import com.example.project.dto.loan.LoanEditDTO;
import com.example.project.exception.BookException;
import com.example.project.exception.LoanException;
import com.example.project.api.service.interfaces.BookServiceAPI;
import com.example.project.api.service.interfaces.LoanServiceAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reader")
@PreAuthorize("hasAnyAuthority('Reader', 'Programmer')")
@CrossOrigin
@Slf4j
public class ReaderController {
    private final BookServiceAPI bookService;
    private final LoanServiceAPI loanService;

    @Autowired
    public ReaderController(BookServiceAPI bookService, LoanServiceAPI loanService) {
        this.bookService = bookService;
        this.loanService = loanService;
    }

    @GetMapping
    public List<BookReaderDTO> getAllBooksForUser(@AuthenticationPrincipal String username) {
        return bookService.getAllBooksForReader(username);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDetailsDTO> getById(@Validated @PathVariable Long id) {
        return new ResponseEntity<>(bookService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/loan")
    public ResponseEntity<LoanDTO> loanBook(@Validated @RequestBody LoanDTO loan) {
        return new ResponseEntity<>(loanService.loanBook(loan), HttpStatus.OK);
    }

    @PostMapping("/return")
    public ResponseEntity<LoanEditDTO> returnBook(@Validated @RequestBody LoanEditDTO loan) {
        return new ResponseEntity<>(loanService.returnBook(loan), HttpStatus.OK);
    }

    @ExceptionHandler(BookException.class)
    public ResponseEntity<String> handleBookExceptionReader(BookException bookException){
        log.error("Reader controller exception " + bookException.getMessage());
        return new ResponseEntity<>(bookException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoanException.class)
    public ResponseEntity<String> handleBookException(LoanException loanException){
        log.error("Book controller exception " + loanException.getMessage());
        return new ResponseEntity<>(loanException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
