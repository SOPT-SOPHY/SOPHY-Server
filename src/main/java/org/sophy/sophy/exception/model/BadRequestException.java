package org.sophy.sophy.exception.model;

import org.sophy.sophy.exception.ErrorStatus;

public class BadRequestException extends SophyException {
    public BadRequestException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }
}
