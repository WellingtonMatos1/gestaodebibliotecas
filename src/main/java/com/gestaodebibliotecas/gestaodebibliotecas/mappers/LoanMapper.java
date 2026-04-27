package com.gestaodebibliotecas.gestaodebibliotecas.mappers;

import com.gestaodebibliotecas.gestaodebibliotecas.dto.LoanCreateDTO;
import com.gestaodebibliotecas.gestaodebibliotecas.dto.LoanUpdateDTO;
import com.gestaodebibliotecas.gestaodebibliotecas.entities.Loan;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    LoanCreateDTO toDTO(Loan entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(LoanUpdateDTO dto, @MappingTarget Loan entity);
}
