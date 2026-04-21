package com.gestaodebibliotecas.gestaodebibliotecas.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tb_books")
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 150)
    private String author;

    @Column(nullable = false, unique = true, length = 13)
    private String isbn;

    @Column(nullable = false, length = 100)
    private String category;

    @Column(name = "publication_date",nullable = false)
    private LocalDate publicationDate;

    @NotNull
    @Setter(AccessLevel.NONE)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void onDeleted () {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public Book(String title, String author, String isbn, String category, LocalDate publicationDate) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.publicationDate = publicationDate;
    }
}
