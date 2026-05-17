package com.gestaodebibliotecas.gestaodebibliotecas.repositories;

import com.gestaodebibliotecas.gestaodebibliotecas.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByIsDeletedFalse(Pageable pageable);

    @Query("""
            SELECT DISTINCT book
            FROM Book book
            WHERE book.isDeleted = false
              AND book.category IN (
                  SELECT DISTINCT borrowedBook.category
                  FROM Loan loan
                  JOIN loan.book borrowedBook
                  WHERE loan.user.id = :userId
              )
              AND book.id NOT IN (
                  SELECT borrowedBook.id
                  FROM Loan loan
                  JOIN loan.book borrowedBook
                  WHERE loan.user.id = :userId
              )
            """)
    Page<Book> findRecommendationsByUserId(@Param("userId") Long userId, Pageable pageable);
}
