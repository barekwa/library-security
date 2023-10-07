package com.example.project.mapper;

import com.example.project.dto.book.*;
import com.example.project.model.Book;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

@UtilityClass
public class BookMapper {
    public Book bookDetailsDTOToBook(BookDetailsDTO bookDetailsDTO){
        Book book = new Book();
        BeanUtils.copyProperties(bookDetailsDTO, book);
        return book;
    }
    public BookDetailsDTO bookToBookDetailsDTO(Book book){
        BookDetailsDTO bookDetailsDTO = new BookDetailsDTO();
        BeanUtils.copyProperties(book, bookDetailsDTO);
        return bookDetailsDTO;
    }
    public BookListDTO bookToBookListDTO(Book book){
        BookListDTO bookListDTO = new BookListDTO();
        BeanUtils.copyProperties(book, bookListDTO);
        return bookListDTO;
    }
    public Book bookDTOToBook(CreateBookDTO createBookDTO){
        Book book = new Book();
        BeanUtils.copyProperties(createBookDTO, book);
        return book;
    }
    public BookReportDTO bookToBookReportDTO(Book book){
        BookReportDTO bookReportDTO = new BookReportDTO();
        BeanUtils.copyProperties(book, bookReportDTO);
        return bookReportDTO;
    }

    public BookReaderDTO bookToBookReaderDTo(Book book){
        BookReaderDTO bookReaderDTO = new BookReaderDTO();
        BeanUtils.copyProperties(book, bookReaderDTO);
        return bookReaderDTO;
    }
}
