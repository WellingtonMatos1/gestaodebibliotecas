package com.gestaodebibliotecas.gestaodebibliotecas.mappers;

import com.gestaodebibliotecas.gestaodebibliotecas.dto.BookDTO;
import com.gestaodebibliotecas.gestaodebibliotecas.entities.Book;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toEntity(BookDTO dto);
    BookDTO toDTO(Book entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(BookDTO dto, @MappingTarget Book entity);
}
