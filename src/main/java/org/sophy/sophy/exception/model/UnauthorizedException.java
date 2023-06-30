package org.sophy.sophy.exception.model;

import org.sophy.sophy.exception.ErrorStatus;

public class UnauthorizedException extends SophyException{
    public UnauthorizedException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }
}
