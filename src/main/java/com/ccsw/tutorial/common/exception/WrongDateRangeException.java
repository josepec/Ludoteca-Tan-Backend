package com.ccsw.tutorial.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WrongDateRangeException extends ResponseStatusException {

    public WrongDateRangeException() {
        super(HttpStatus.CONFLICT,
                "El rango de fechas es superior a 14 días, o la fecha de inicio es posterior a la de finalización.");
    }

}