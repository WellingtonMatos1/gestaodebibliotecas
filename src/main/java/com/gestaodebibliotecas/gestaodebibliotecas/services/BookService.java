package com.gestaodebibliotecas.gestaodebibliotecas.services;

import com.gestaodebibliotecas.gestaodebibliotecas.dto.BookDTO;
import com.gestaodebibliotecas.gestaodebibliotecas.entities.Book;
import com.gestaodebibliotecas.gestaodebibliotecas.exception.ResourceNotFoundException;
import com.gestaodebibliotecas.gestaodebibliotecas.mappers.BookMapper;
import com.gestaodebibliotecas.gestaodebibliotecas.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private static final String BOOK_NOT_FOUND = "Book not found";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    public Page<BookDTO> findAll(Pageable pageable) {
        return bookRepository.findByIsDeletedFalse(pageable)
                .map(BookDTO::new);
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
        Book entity = bookMapper.toEntity(bookDTO);
        return bookMapper.toDTO(bookRepository.save(entity));
    }

    @Transactional
    public BookDTO updateBook (Long id, BookDTO bookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(BOOK_NOT_FOUND));

        bookMapper.updateEntityFromDto(bookDTO, existingBook);
        return bookMapper.toDTO(bookRepository.save(existingBook));
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
