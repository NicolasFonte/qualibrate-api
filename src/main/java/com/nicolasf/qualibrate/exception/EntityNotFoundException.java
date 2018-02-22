package com.nicolasf.qualibrate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Entity Not Found.")
public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
