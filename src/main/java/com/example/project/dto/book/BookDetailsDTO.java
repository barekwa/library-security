package com.example.project.dto.book;

import com.example.project.model.Loan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDetailsDTO {

    @NotEmpty
    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    @NotEmpty
    private String genre;

    @NotEmpty
    private int quantity;

    @NotEmpty
    private LocalDateTime releaseDate;

    private List<Loan> loans;
}
