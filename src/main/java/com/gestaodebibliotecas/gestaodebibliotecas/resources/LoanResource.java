package com.gestaodebibliotecas.gestaodebibliotecas.resources;

import com.gestaodebibliotecas.gestaodebibliotecas.dto.LoanCreateDTO;
import com.gestaodebibliotecas.gestaodebibliotecas.dto.LoanUpdateDTO;
import com.gestaodebibliotecas.gestaodebibliotecas.services.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/loans")
public class LoanResource {

    @Autowired
    private LoanService loanService;

    @GetMapping
    public ResponseEntity<Page<LoanCreateDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(loanService.findAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LoanCreateDTO> findById(@PathVariable Long id) {
        LoanCreateDTO obj = loanService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<LoanCreateDTO> createLoan(@RequestBody @Valid LoanCreateDTO loanCreateDTO) {
        LoanCreateDTO savedLoan = loanService.saveLoan(loanCreateDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedLoan.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedLoan);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<LoanCreateDTO> updateLoan(@PathVariable Long id, @RequestBody @Valid LoanUpdateDTO loanUpdateDTO) {
        LoanCreateDTO updateUser = loanService.updateLoan(id, loanUpdateDTO);
        return ResponseEntity.ok().body(updateUser);
    }
}
