package com.protec.book.controller;

import com.protec.book.domain.Book;
import com.protec.book.dtos.BookRecordDto;
import com.protec.book.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping("teste")
    public String test() {
        return "Hello World";
    }

    @PostMapping
    public ResponseEntity<Book> save(@RequestBody  @Valid BookRecordDto bookDto) {
        var book = service.save(bookDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }





}
