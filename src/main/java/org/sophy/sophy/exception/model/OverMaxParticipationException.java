package org.sophy.sophy.exception.model;

import org.sophy.sophy.exception.ErrorStatus;

public class OverMaxParticipationException extends SophyException{
    public OverMaxParticipationException(ErrorStatus errorStatus, String message) {
        super(errorStatus, message);
    }
}
