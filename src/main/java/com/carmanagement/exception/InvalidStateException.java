package com.carmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Shwetha on 16-11-2018.
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidStateException extends Exception {
    static final long serialVersionUID = -3387516993334229948L;
    String message = "Entity is in Invalid State";

    public InvalidStateException(String message) {
        super(message);
        this.message = message;
    }

}