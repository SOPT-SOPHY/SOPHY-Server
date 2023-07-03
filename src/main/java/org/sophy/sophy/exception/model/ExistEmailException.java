package org.sophy.sophy.exception.model;

import org.sophy.sophy.exception.ErrorStatus;

public class ExistEmailException extends SophyException {

    public ExistEmailException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }
}
