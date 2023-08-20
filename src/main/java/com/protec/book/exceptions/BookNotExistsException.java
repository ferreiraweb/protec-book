package com.protec.book.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class BookNotExistsException extends RuntimeException {

    private static final Long serialVersionUID = 1L;

    public BookNotExistsException(String message) {
        super(message);
    }
}
