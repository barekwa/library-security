package com.example.project.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class BookListDTO {

    @NotEmpty
    private Long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String author;


}
