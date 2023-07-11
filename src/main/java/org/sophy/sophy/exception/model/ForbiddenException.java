package org.sophy.sophy.exception.model;

import org.sophy.sophy.exception.ErrorStatus;


public class ForbiddenException extends SophyException {
    public ForbiddenException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }
}
