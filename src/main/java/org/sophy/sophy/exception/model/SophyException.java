package org.sophy.sophy.exception.model;

import lombok.Getter;
import org.sophy.sophy.exception.ErrorStatus;

@Getter
public class SophyException extends RuntimeException {
    private final ErrorStatus errorStatus;

    public SophyException(ErrorStatus errorStatus, String message) {
        super(message);
        this.errorStatus = errorStatus;
    }

    public Integer getHttpStatus() {
        return errorStatus.getHttpStatusCode();
    }
}
