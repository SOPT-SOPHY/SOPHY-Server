package org.sophy.sophy.exception.model;

import org.sophy.sophy.exception.ErrorStatus;

public class DuplParticipationException extends SophyException {

    public DuplParticipationException(ErrorStatus errorStatus,
        String message) {
        super(errorStatus, message);
    }
}
