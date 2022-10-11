package com.ccsw.tutorial.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlreadyExistsException extends ResponseStatusException {

    public AlreadyExistsException() {
        super(HttpStatus.CONFLICT, "El nombre del cliente ya existe.");
    }

}