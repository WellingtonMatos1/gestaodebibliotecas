package com.gestaodebibliotecas.gestaodebibliotecas.mappers;

import com.gestaodebibliotecas.gestaodebibliotecas.dto.UserDTO;
import com.gestaodebibliotecas.gestaodebibliotecas.entities.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO dto);
    UserDTO toDTO(User entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UserDTO dto, @MappingTarget User entity);
}
