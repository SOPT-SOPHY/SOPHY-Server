package org.sophy.sophy.exception.model;

import org.sophy.sophy.exception.ErrorStatus;

public class ExpiredRefreshTokenException extends SophyException {

    public ExpiredRefreshTokenException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }
}
