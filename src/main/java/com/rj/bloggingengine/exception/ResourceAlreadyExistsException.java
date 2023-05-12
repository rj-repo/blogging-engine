package com.rj.bloggingengine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(String msg) {
        super(msg);
    }
}
