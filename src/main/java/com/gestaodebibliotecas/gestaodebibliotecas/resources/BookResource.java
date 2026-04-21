package com.gestaodebibliotecas.gestaodebibliotecas.resources;

import com.gestaodebibliotecas.gestaodebibliotecas.dto.BookDTO;
import com.gestaodebibliotecas.gestaodebibliotecas.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/books")
public class BookResource {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO>> findAll() {
        List<BookDTO> list = bookService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookDTO> findById (@PathVariable Long id) {
        BookDTO obj = bookService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<BookDTO> updateBook (@RequestBody @Valid BookDTO bookDTO) {
        BookDTO savedBook = bookService.saveBook(bookDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBook.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedBook);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BookDTO> updateBook (@PathVariable Long id, @RequestBody @Valid BookDTO bookDTO){
        BookDTO updateBook = bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok().body(updateBook);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteBook (@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}