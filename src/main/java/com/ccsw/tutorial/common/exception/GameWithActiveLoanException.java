package com.ccsw.tutorial.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GameWithActiveLoanException extends ResponseStatusException {

    public GameWithActiveLoanException() {
        super(HttpStatus.CONFLICT,
                "El juego ya está siendo prestado, uno o varios días, en el rango de fechas seleccionado.");
    }

}