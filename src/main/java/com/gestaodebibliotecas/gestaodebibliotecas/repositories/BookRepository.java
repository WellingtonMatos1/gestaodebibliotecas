package com.gestaodebibliotecas.gestaodebibliotecas.repositories;

import com.gestaodebibliotecas.gestaodebibliotecas.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByIsDeletedFalse();
}
