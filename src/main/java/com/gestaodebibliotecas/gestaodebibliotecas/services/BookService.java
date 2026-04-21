package com.gestaodebibliotecas.gestaodebibliotecas.services;

import com.gestaodebibliotecas.gestaodebibliotecas.dto.BookDTO;
import com.gestaodebibliotecas.gestaodebibliotecas.entities.Book;
import com.gestaodebibliotecas.gestaodebibliotecas.exception.ResourceNotFoundException;
import com.gestaodebibliotecas.gestaodebibliotecas.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    private static final String BOOK_NOT_FOUND = "Book not found";

    @Autowired
    private BookRepository bookRepository;

    public List<BookDTO> findAll() {
        return bookRepository.findByIsDeletedFalse()
                .stream()
                .map(BookDTO::new)
                .toList();
    }

    public BookDTO findById(Long id) {
        Book entity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(BOOK_NOT_FOUND));

        if(entity.isDeleted()) {
            throw new ResourceNotFoundException(BOOK_NOT_FOUND);
        }

        return new BookDTO(entity);
    }

    @Transactional
    public BookDTO saveBook (BookDTO bookDTO) {
        Book entity = new Book();
        entity.setTitle(bookDTO.getTitle());
        entity.setAuthor(bookDTO.getAuthor());
        entity.setIsbn(bookDTO.getIsbn());
        entity.setCategory(bookDTO.getCategory());
        entity.setPublicationDate(bookDTO.getPublicationDate());

        return new BookDTO(bookRepository.save(entity));
    }

    @Transactional
    public BookDTO updateBook (Long id, BookDTO bookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(BOOK_NOT_FOUND));

        if(existingBook.isDeleted()) {
            throw new ResourceNotFoundException(BOOK_NOT_FOUND);
        }
        if(bookDTO.getTitle() != null) {
            existingBook.setTitle(bookDTO.getTitle());
        }
        if(bookDTO.getAuthor() != null) {
            existingBook.setAuthor(bookDTO.getAuthor());
        }
        if(bookDTO.getIsbn() != null) {
            existingBook.setIsbn(bookDTO.getIsbn());
        }
        if(bookDTO.getCategory() != null) {
            existingBook.setCategory(bookDTO.getCategory());
        }
        if(bookDTO.getPublicationDate() != null) {
            existingBook.setPublicationDate(bookDTO.getPublicationDate());
        }
        return new BookDTO(bookRepository.save(existingBook));
    }

    @Transactional
    public void deleteBook (Long id) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(BOOK_NOT_FOUND));

        if(existingBook.isDeleted()) {
            throw new ResourceNotFoundException(BOOK_NOT_FOUND);
        }
        existingBook.onDeleted();

        bookRepository.save(existingBook);
    }
}
