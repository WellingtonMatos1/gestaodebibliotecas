package com.gestaodebibliotecas.gestaodebibliotecas.dto;

import com.gestaodebibliotecas.gestaodebibliotecas.entities.enums.LoanStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class LoanUpdateDTO {

    private LoanStatus status;

    private LocalDate loanDate;

    @NotNull
    @Future
    private LocalDate returnDate;
}
