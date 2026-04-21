package com.gestaodebibliotecas.gestaodebibliotecas.repositories;

import com.gestaodebibliotecas.gestaodebibliotecas.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail (String email);
    List<User> findByIsDeletedFalse();
}
