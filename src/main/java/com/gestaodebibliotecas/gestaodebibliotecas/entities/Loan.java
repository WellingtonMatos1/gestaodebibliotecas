package com.gestaodebibliotecas.gestaodebibliotecas.entities;

import com.gestaodebibliotecas.gestaodebibliotecas.entities.enums.LoanStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_loans")
public class Loan implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @NotNull
    @PastOrPresent
    @Setter(AccessLevel.NONE)
    @Column(name = "loan_date", nullable = false)
        private LocalDate loanDate;

    @NotNull
    @Future
    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoanStatus status;
}
