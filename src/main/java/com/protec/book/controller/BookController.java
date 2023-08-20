package com.protec.book.controller;

import com.protec.book.dtos.BookRecordDto;
import com.protec.book.exceptions.BookNotExistsException;
import com.protec.book.exceptions.CustomResponseExceptionRecord;
import com.protec.book.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService service;
    private ObjectError e;

    @GetMapping("/teste")
    public String test() {
        return "Hello World";
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody BookRecordDto dto, Errors validations) {
        if (validations.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(getCustomRecordExceptionResponse(validations, "Erros de validação"));
        }

        BookRecordDto bookDto = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value="id") Long id)  {

        var responseBookDto = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseBookDto);
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody @Valid BookRecordDto dto, Errors validations){

        if (validations.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(getCustomRecordExceptionResponse(validations, "Erros de validação" ));
        }

        BookRecordDto responseBookDto = service.update(dto);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseBookDto);
    }





    /* --------------------------------------------------------------------------- */

    private CustomResponseExceptionRecord getCustomRecordExceptionResponse(Errors validations, String errorsDetails) {
        String[] allErros = validations.getAllErrors()
                .stream()
                .map(err -> err.getDefaultMessage())
                .toArray(String[]::new);

        CustomResponseExceptionRecord customRecordExceptionResponse = new CustomResponseExceptionRecord(
                new Date(),
                errorsDetails,
                allErros
        );

        return customRecordExceptionResponse;
    }


}
