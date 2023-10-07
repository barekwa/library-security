package com.example.project.dto.loan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class LoanEditDTO {

    @NotEmpty
    private Long id;

    @NotEmpty
    private Long bookId;

    @NotEmpty
    private Long userId;

    @NotEmpty
    private boolean returned;
}
