package com.gestaodebibliotecas.gestaodebibliotecas.services;

import com.gestaodebibliotecas.gestaodebibliotecas.dto.UserDTO;
import com.gestaodebibliotecas.gestaodebibliotecas.entities.User;
import com.gestaodebibliotecas.gestaodebibliotecas.exception.BusinessException;
import com.gestaodebibliotecas.gestaodebibliotecas.exception.ResourceNotFoundException;
import com.gestaodebibliotecas.gestaodebibliotecas.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private static final String USER_NOT_FOUND = "User not found";
    private static final String EMAIL_ALREADY_REGISTERED = "Email already registered";

    public List<UserDTO> findAll() {
        return userRepository.findByIsDeletedFalse()
                .stream()
                .map(UserDTO::new)
                .toList();
    }

    public UserDTO findById(Long id) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

        if (entity.isDeleted()) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }

        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO saveUser(UserDTO userDTO) {
        if(userRepository.existsByEmail(userDTO.getEmail())) {
            throw new BusinessException(EMAIL_ALREADY_REGISTERED);
        }
        User entity = new User();
        entity.setName(userDTO.getName());
        entity.setEmail(userDTO.getEmail());
        entity.setPhone(userDTO.getPhone());

        return new UserDTO(userRepository.save(entity));
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

        if(existingUser.isDeleted()) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }
        if(userDTO.getName() != null) {
            existingUser.setName(userDTO.getName());
        }
        if(userDTO.getEmail() != null) {
            if(userRepository.existsByEmail(userDTO.getEmail())) {
                throw new BusinessException(EMAIL_ALREADY_REGISTERED);
            }
            existingUser.setEmail(userDTO.getEmail());
        }
        if(userDTO.getPhone() != null) {
            existingUser.setPhone(userDTO.getPhone());
        }

        return new UserDTO(userRepository.save(existingUser));
    }

    @Transactional
    public void deleteUser (Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

        existingUser.onDeleted();

        userRepository.save(existingUser);
    }
}
