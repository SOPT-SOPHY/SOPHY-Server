package org.sophy.sophy.exception.model;

import org.sophy.sophy.exception.ErrorStatus;

public class LogoutRefreshtokenException extends SophyException {

    public LogoutRefreshtokenException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }
}
