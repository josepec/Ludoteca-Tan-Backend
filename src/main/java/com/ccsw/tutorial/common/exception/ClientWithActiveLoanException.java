package com.ccsw.tutorial.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClientWithActiveLoanException extends ResponseStatusException {

    public ClientWithActiveLoanException() {
        super(HttpStatus.CONFLICT,
                "El cliente ya tiene un préstamo, en uno o varios días, del rango de fechas seleccionado.");
    }

}