package com.gestaodebibliotecas.gestaodebibliotecas.services;

import com.gestaodebibliotecas.gestaodebibliotecas.dto.LoanCreateDTO;
import com.gestaodebibliotecas.gestaodebibliotecas.dto.LoanUpdateDTO;
import com.gestaodebibliotecas.gestaodebibliotecas.entities.Book;
import com.gestaodebibliotecas.gestaodebibliotecas.entities.Loan;
import com.gestaodebibliotecas.gestaodebibliotecas.entities.User;
import com.gestaodebibliotecas.gestaodebibliotecas.entities.enums.LoanStatus;
import com.gestaodebibliotecas.gestaodebibliotecas.exception.BusinessException;
import com.gestaodebibliotecas.gestaodebibliotecas.exception.ResourceNotFoundException;
import com.gestaodebibliotecas.gestaodebibliotecas.mappers.LoanMapper;
import com.gestaodebibliotecas.gestaodebibliotecas.repositories.BookRepository;
import com.gestaodebibliotecas.gestaodebibliotecas.repositories.LoanRepository;
import com.gestaodebibliotecas.gestaodebibliotecas.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoanService {

    private static final String LOAN_NOT_FOUND = "Loan not found";
    private static final String BOOK_NOT_FOUND = "Book not found";
    private static final String USER_NOT_FOUND = "User not found";
    private static final String BOOK_ALREADY_LOANED = "Book already has an active loan";

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanMapper loanMapper;

    public Page<LoanCreateDTO> findAll(Pageable pageable) {
        return loanRepository.findAll(pageable)
                .map(LoanCreateDTO::new);
    }

    public LoanCreateDTO findById(Long id) {
        Loan entity = loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(LOAN_NOT_FOUND));

        return new LoanCreateDTO(entity);
    }

    @Transactional
    public LoanCreateDTO saveLoan(LoanCreateDTO loanCreateDTO) {
        User user = userRepository.findById(loanCreateDTO.getUserId())
                .filter(u -> !u.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

        Book book = bookRepository.findById(loanCreateDTO.getBookId())
                .filter(b -> !b.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException(BOOK_NOT_FOUND));

        if(loanRepository.existsByBookAndStatus(book, LoanStatus.BORROWED)) {
            throw new BusinessException(BOOK_ALREADY_LOANED);
        }

        Loan entity = new Loan();
        entity.setUser(user);
        entity.setBook(book);
        entity.setReturnDate(loanCreateDTO.getReturnDate());

        return loanMapper.toDTO(loanRepository.save(entity));
    }

    @Transactional
    public LoanCreateDTO updateLoan(Long id, LoanUpdateDTO loanUpdateDTO) {
        Loan existingLoan = loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(LOAN_NOT_FOUND));

        loanMapper.updateEntityFromDto(loanUpdateDTO, existingLoan);
        return loanMapper.toDTO(loanRepository.save(existingLoan));
    }
}
