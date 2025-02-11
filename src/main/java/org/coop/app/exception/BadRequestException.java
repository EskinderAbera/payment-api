package org.coop.app.exception;

public class BadRequestException extends RuntimeException {
    private int errorCode;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getErrorCode() {
        return errorCode;
    }
}
