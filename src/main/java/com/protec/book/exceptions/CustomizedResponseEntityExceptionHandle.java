package com.protec.book.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandle extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<CustomRecordExceptionResponse> handleAllException(Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(getCustomRecordExceptionResponse(ex));
    }

    @ExceptionHandler(BookNotExistsException.class)
    public ResponseEntity<?> handleBookNotExistsException(BookNotExistsException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex);
    }

    /* ------------------------------------------------------------------------------------ */

    private CustomRecordExceptionResponse getCustomRecordExceptionResponse(Exception ex){
        CustomRecordExceptionResponse exceptionResponse = new CustomRecordExceptionResponse(
                new Date(),
                ex.getMessage(),
                null
        );

        System.out.println(ex);

        return exceptionResponse;
    }

}
