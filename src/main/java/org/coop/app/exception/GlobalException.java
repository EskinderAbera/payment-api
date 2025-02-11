package org.coop.app.exception;


class ErrorResponse {
    public String status;
    public String message;

    public ErrorResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}

