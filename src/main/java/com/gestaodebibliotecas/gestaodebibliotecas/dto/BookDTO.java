package com.gestaodebibliotecas.gestaodebibliotecas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gestaodebibliotecas.gestaodebibliotecas.entities.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 150)
    private String title;

    @NotBlank
    @Size(min = 2, max = 150)
    private String author;

    @NotBlank
    @Pattern(regexp = "^(?:\\d{9}[\\dX]|\\d{13})$", message = "ISBN invalid")
    private String isbn;

    @NotBlank
    @Size(min = 2, max = 100)
    private String category;

    @NotNull
    private LocalDate publicationDate;

    public BookDTO () {}

    public BookDTO(Book entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.isbn = entity.getIsbn();
        this.category = entity.getCategory();
        this.publicationDate = entity.getPublicationDate();
    }
}
