package org.sophy.sophy.exception.model;

import org.sophy.sophy.exception.ErrorStatus;

public class InvalidPasswordException extends SophyException {

    public InvalidPasswordException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }
}
