package com.gestaodebibliotecas.gestaodebibliotecas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gestaodebibliotecas.gestaodebibliotecas.entities.Loan;
import com.gestaodebibliotecas.gestaodebibliotecas.entities.enums.LoanStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class LoanCreateDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long bookId;

    private LocalDate loanDate;

    @NotNull
    @Future
    private LocalDate returnDate;

    private LoanStatus status;

    public LoanCreateDTO(Loan entity) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.bookId = entity.getBook().getId();
        this.loanDate = entity.getLoanDate();
        this.returnDate = entity.getReturnDate();
        this.status = entity.getStatus();
    }
}
