package com.example.project.api.service.implementations;

import com.example.project.dto.book.BookReportDTO;
import com.example.project.mapper.BookMapper;
import com.example.project.api.repository.BookRepository;
import com.example.project.api.repository.LoanRepository;
import com.example.project.api.service.interfaces.BookRaportServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookRaportService implements BookRaportServiceAPI {
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    @Autowired
    public BookRaportService(BookRepository bookRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public List<BookReportDTO> generateRaport() {
        return bookRepository.findAll().stream()
                .map(book -> {
                    BookReportDTO bookReportDTO = BookMapper.bookToBookReportDTO(book);
                    bookReportDTO.setNumOfLoans(loanRepository.countLoansForBook(book.getId()));
                    return bookReportDTO;
                })
                .toList();
    }
}
