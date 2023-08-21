package com.protec.book.exceptions;

public class IdMustBeNonNullException extends RuntimeException {
    private static final Long serialVersionUID = 1L;

    public IdMustBeNonNullException(String message) {
        super(message);
    }
}
