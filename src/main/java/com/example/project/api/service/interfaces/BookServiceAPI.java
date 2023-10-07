package com.example.project.api.service.interfaces;

import com.example.project.dto.book.BookDetailsDTO;
import com.example.project.dto.book.BookListDTO;
import com.example.project.dto.book.BookReaderDTO;
import com.example.project.dto.book.CreateBookDTO;
import com.example.project.model.Book;

import java.util.List;

public interface BookServiceAPI {
     BookDetailsDTO findById(Long id);

     List<BookListDTO> getAll();

     List<BookReaderDTO> getAllBooksForReader(String username);

     Book save(CreateBookDTO book);

     void delete(Long id);

     Book update(BookDetailsDTO book);
 }
