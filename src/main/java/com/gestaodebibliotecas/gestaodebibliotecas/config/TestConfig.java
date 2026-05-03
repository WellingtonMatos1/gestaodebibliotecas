package com.gestaodebibliotecas.gestaodebibliotecas.config;

import com.gestaodebibliotecas.gestaodebibliotecas.entities.Book;
import com.gestaodebibliotecas.gestaodebibliotecas.entities.Loan;
import com.gestaodebibliotecas.gestaodebibliotecas.entities.User;
import com.gestaodebibliotecas.gestaodebibliotecas.repositories.BookRepository;
import com.gestaodebibliotecas.gestaodebibliotecas.repositories.LoanRepository;
import com.gestaodebibliotecas.gestaodebibliotecas.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    @Override
    public void run(String... args) throws Exception {

        User u1 = new User("teste", "testetes@hotmail.com", "1111111111111111111111");
        User u2 = new User("dsadsadsa", "dsadsa@hotmail.com", "321321321321");

        userRepository.saveAll(Arrays.asList(u1, u2));

        Book b1 = new Book("Terror teste", "testador1", "1111111111111", "Terror", LocalDate.parse("2020-12-20"));
        Book b2 = new Book("Ficção teste", "testador2", "2222222222222", "Ficção Científica", LocalDate.parse("2025-01-03"));
        Book b3 = new Book("Terror teste 2", "testador3", "3333333333333", "Terror", LocalDate.parse("2019-05-10"));
        Book b4 = new Book("Terror teste 3", "testador4", "4444444444444", "Terror", LocalDate.parse("2021-08-15"));
        Book b5 = new Book("Ficção teste 2", "testador5", "5555555555555", "Ficção Científica", LocalDate.parse("2022-03-22"));
        Book b6 = new Book("Aventura teste", "testador6", "6666666666666", "Aventura", LocalDate.parse("2018-11-30"));
        Book b7 = new Book("Aventura teste 2", "testador7", "7777777777777", "Aventura", LocalDate.parse("2023-07-04"));
        Book b8 = new Book("Romance teste", "testador8", "8888888888888", "Romance", LocalDate.parse("2024-02-14"));

        bookRepository.saveAll(Arrays.asList(b1, b2, b3, b4, b5, b6, b7, b8));

        Loan l1 = new Loan(u1, b1, LocalDate.parse("2025-05-10"));
        Loan l2 = new Loan(u1, b3, LocalDate.parse("2025-05-20"));
        Loan l3 = new Loan(u1, b7, LocalDate.parse("2025-06-01"));
        Loan l4 = new Loan(u2, b2, LocalDate.parse("2025-05-15"));
        Loan l5 = new Loan(u2, b5, LocalDate.parse("2025-05-25"));

        loanRepository.saveAll(Arrays.asList(l1, l2, l3, l4, l5));
    }
}
