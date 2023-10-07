package com.example.project.api.service.implementations;

import com.example.project.dto.book.BookDetailsDTO;
import com.example.project.dto.book.BookListDTO;
import com.example.project.dto.book.BookReaderDTO;
import com.example.project.dto.book.CreateBookDTO;
import com.example.project.dto.loan.LoanDTO;
import com.example.project.exception.BookException;
import com.example.project.mapper.BookMapper;
import com.example.project.model.Book;
import com.example.project.api.repository.BookRepository;
import com.example.project.api.service.interfaces.BookServiceAPI;
import com.example.project.api.service.interfaces.LoanServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService implements BookServiceAPI {
    private final BookRepository bookRepository;
    private final LoanServiceAPI loanService;
    @Autowired
    public BookService(BookRepository bookRepository, LoanServiceAPI loanService) {

        this.bookRepository = bookRepository;
        this.loanService = loanService;
    }

    @Override
    public BookDetailsDTO findById(Long id){

        return bookRepository.findById(id).
                map(BookMapper::bookToBookDetailsDTO)
                .orElseThrow(() -> new BookException("Book not found"));

    }

    @Override
    public List<BookListDTO> getAll(){

        return bookRepository.findAll().stream()
                .map(BookMapper::bookToBookListDTO)
                .toList();

    }

    @Override
    public List<BookReaderDTO> getAllBooksForReader(String username) {
        List<LoanDTO> userLoans = loanService.getLoansByUserName(username);

        return bookRepository.findAll().stream()
                .map(book -> {
                    BookReaderDTO bookReaderDTO = BookMapper.bookToBookReaderDTo(book);
                    boolean isBorrowed = userLoans.stream()
                            .anyMatch(loanDTO -> loanDTO.getBookId().equals(bookReaderDTO.getId()));
                    bookReaderDTO.setBorrowed(isBorrowed);
                    return bookReaderDTO;
                })
                .toList();
    }

    @Override
    public Book save(CreateBookDTO book){

        return Optional.of(book)
                .map(BookMapper::bookDTOToBook)
                .map(bookRepository::save)
                .orElseThrow(() -> new BookException("Cannot create book"));

    }

    @Override
    public void delete(Long id){

        bookRepository.findById(id).ifPresentOrElse(
                bookRepository::delete,
                () -> {
                    throw new BookException("Book not found");
                }
        );
    }

    @Override
    public Book update(BookDetailsDTO book){

        return bookRepository.findById(book.getId())
                .map(bookToUpdate -> BookMapper.bookDetailsDTOToBook(book))
                .map(bookRepository::save)
                .orElseThrow(() -> new BookException("Cannot update book"));

    }
}
