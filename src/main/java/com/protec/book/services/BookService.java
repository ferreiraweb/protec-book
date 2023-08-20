package com.protec.book.services;

import com.protec.book.domain.Book;
import com.protec.book.dtos.BookRecordDto;
import com.protec.book.repositories.BookRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    public Book save(@NotNull BookRecordDto bookDto) {
       return repository.save(bookDto.ToEntity());
    }


}
