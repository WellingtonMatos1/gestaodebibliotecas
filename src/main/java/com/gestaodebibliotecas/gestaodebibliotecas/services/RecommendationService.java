package com.gestaodebibliotecas.gestaodebibliotecas.services;

import com.gestaodebibliotecas.gestaodebibliotecas.dto.BookDTO;
import com.gestaodebibliotecas.gestaodebibliotecas.exception.ResourceNotFoundException;
import com.gestaodebibliotecas.gestaodebibliotecas.repositories.BookRepository;
import com.gestaodebibliotecas.gestaodebibliotecas.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RecommendationService {

    private static final String USER_NOT_FOUND = "User not found";

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<BookDTO> recommendBooksByUser(Long userId, Pageable pageable) {
        userRepository.findById(userId)
                .filter(user -> !user.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

        return bookRepository.findRecommendationsByUserId(userId, pageable)
                .map(BookDTO::new);
    }
}
