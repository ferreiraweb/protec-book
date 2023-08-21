package com.protec.book.exceptions;

public class BookNameCannotBeRepeatedException extends RuntimeException {

    private static final Long serialVersionUID = 1L;

    public BookNameCannotBeRepeatedException(String message) {
        super(message);
    }


}
