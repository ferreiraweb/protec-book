package com.protec.book.exceptions;

public class BookNotExistsException extends RuntimeException {

    private static final Long serialVersionUID = 1L;

    public BookNotExistsException(String message) {
        super(message);
    }
}
