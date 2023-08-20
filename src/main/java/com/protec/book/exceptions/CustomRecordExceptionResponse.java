package com.protec.book.exceptions;
import java.util.Date;

public record CustomRecordExceptionResponse(
        Date timestamp,
        String details,
        String[] messages
){}






