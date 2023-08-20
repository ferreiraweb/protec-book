package com.protec.book.exceptions;
import java.util.Date;

public record CustomResponseExceptionRecord(
        Date timestamp,
        String details,
        String[] messages
){}






