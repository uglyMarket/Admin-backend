package com.sparta.uglymarket.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private final HttpStatus httpStatus;

    public CustomException(ErrorMsg errorMsg) {
        super(errorMsg.getDetails());
        this.httpStatus = errorMsg.getHttpStatus();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
