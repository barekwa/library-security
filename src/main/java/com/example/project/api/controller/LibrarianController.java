package com.example.project.api.controller;

import com.example.project.dto.book.BookDetailsDTO;
import com.example.project.dto.book.BookListDTO;
import com.example.project.dto.book.BookReportDTO;
import com.example.project.dto.book.CreateBookDTO;
import com.example.project.exception.BookException;
import com.example.project.model.Book;
import com.example.project.api.service.interfaces.BookRaportServiceAPI;
import com.example.project.api.service.interfaces.BookServiceAPI;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@PreAuthorize("hasAnyAuthority('Librarian', 'Programmer')")
@Slf4j
@AllArgsConstructor
public class LibrarianController {

    private final BookServiceAPI bookService;
    private final BookRaportServiceAPI bookRaportService;

    @GetMapping
    public List<BookListDTO> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDetailsDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Book> save(@Validated @RequestBody CreateBookDTO bookDTO) {
        return new ResponseEntity<>(bookService.save(bookDTO), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Book> update(@Validated @RequestBody BookDetailsDTO bookDTO) {
        return new ResponseEntity<>(bookService.update(bookDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
            bookService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/raport")
    public List<BookReportDTO> getRaport() {
        return bookRaportService.generateRaport();
    }

    @ExceptionHandler(BookException.class)
    public ResponseEntity<String> handleBookExceptionLibrarian(BookException bookException){
        log.error("Librarian controller exception " + bookException.getMessage());
        return new ResponseEntity<>(bookException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
