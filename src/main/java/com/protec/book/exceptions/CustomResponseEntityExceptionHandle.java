package com.protec.book.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class CustomResponseEntityExceptionHandle extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<CustomResponseExceptionRecord> handleGlobalException(Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(getCustomRecordExceptionResponse(ex));
    }

    @ExceptionHandler(BookNotExistsException.class)
    public ResponseEntity<CustomResponseExceptionRecord> handleBookNotExistsException
            (BookNotExistsException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(getCustomRecordExceptionResponse(ex));
    }

    @ExceptionHandler(IdMustBeNonNullException.class)
    public ResponseEntity<CustomResponseExceptionRecord> handleIdMustBeNonNullException(
            IdMustBeNonNullException ex
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(getCustomRecordExceptionResponse(ex));
    }


    public ResponseEntity<CustomResponseExceptionRecord> handleBookNameCannotBeRepeatedException(
            BookNameCannotBeRepeatedException ex )
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getCustomRecordExceptionResponse(ex));
    }

    /* ------------------------------------------------------------------------------------ */

    private CustomResponseExceptionRecord getCustomRecordExceptionResponse(Exception ex) {
        CustomResponseExceptionRecord exceptionResponse = new CustomResponseExceptionRecord(
                new Date(),
                ex.getMessage(),
                null
        );

        return exceptionResponse;
    }

}
