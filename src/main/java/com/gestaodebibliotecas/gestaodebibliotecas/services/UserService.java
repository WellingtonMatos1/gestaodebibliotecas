package com.gestaodebibliotecas.gestaodebibliotecas.services;

import com.gestaodebibliotecas.gestaodebibliotecas.dto.UserDTO;
import com.gestaodebibliotecas.gestaodebibliotecas.entities.User;
import com.gestaodebibliotecas.gestaodebibliotecas.exception.BusinessException;
import com.gestaodebibliotecas.gestaodebibliotecas.exception.ResourceNotFoundException;
import com.gestaodebibliotecas.gestaodebibliotecas.mappers.UserMapper;
import com.gestaodebibliotecas.gestaodebibliotecas.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    private static final String USER_NOT_FOUND = "User not found";
    private static final String EMAIL_ALREADY_REGISTERED = "Email already registered";

    public Page<UserDTO> findAll(Pageable pageable) {
        return userRepository.findByIsDeletedFalse(pageable)
                .map(UserDTO::new);
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
        User entity = userMapper.toEntity(userDTO);
        return userMapper.toDTO(userRepository.save(entity));
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

        if(existingUser.isDeleted()) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }
        if(userDTO.getEmail() != null) {
            if(userRepository.existsByEmail(userDTO.getEmail())) {
                throw new BusinessException(EMAIL_ALREADY_REGISTERED);
            }
            existingUser.setEmail(userDTO.getEmail());
        }

        userMapper.updateEntityFromDto(userDTO, existingUser);
        return userMapper.toDTO(userRepository.save(existingUser));
    }

    @Transactional
    public void deleteUser (Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));

        if(existingUser.isDeleted()) {
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        }
        existingUser.onDeleted();

        userRepository.save(existingUser);
    }
}
