package com.gestaodebibliotecas.gestaodebibliotecas.repositories;

import com.gestaodebibliotecas.gestaodebibliotecas.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail (String email);
    Page<User> findByIsDeletedFalse(Pageable pageable);
}
