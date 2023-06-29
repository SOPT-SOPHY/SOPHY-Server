package org.sophy.sophy.exception.model;

import org.sophy.sophy.exception.ErrorStatus;

public class NotFoundException extends SophyException {

    public NotFoundException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }
}
