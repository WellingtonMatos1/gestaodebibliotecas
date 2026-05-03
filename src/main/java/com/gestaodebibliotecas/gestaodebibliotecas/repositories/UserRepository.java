package com.gestaodebibliotecas.gestaodebibliotecas.repositories;

import com.gestaodebibliotecas.gestaodebibliotecas.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail (String email);
    boolean existsByEmailAndIdNotAndIsDeletedFalse (String email, Long id);
    Page<User> findByIsDeletedFalse(Pageable pageable);
}
