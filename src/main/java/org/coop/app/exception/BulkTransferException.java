package org.coop.app.exception;

public class BulkTransferException extends RuntimeException {
    public BulkTransferException(String message, Throwable cause) {
        super(message, cause);
    }
}

