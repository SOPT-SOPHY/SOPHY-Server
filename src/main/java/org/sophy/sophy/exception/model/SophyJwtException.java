package org.sophy.sophy.exception.model;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;

public class SophyJwtException extends JwtException {
    private final HttpStatus httpStatus;

    public SophyJwtException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
    public Integer getHttpStatus() {
        return httpStatus.value();
    }
}
