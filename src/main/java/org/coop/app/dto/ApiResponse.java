package org.coop.app.dto;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    // Getters and Setters
    private final int statusCode;
    private final String message;
    private final T payload;
    private final String error;

    private ApiResponse(int statusCode, String message, T payload, String error) {
        this.statusCode = statusCode;
        this.message = message;
        this.payload = payload;
        this.error = error;
    }

    public static <T> ApiResponse<T> success(T payload, String message) {
        return new ApiResponse<>(200, message, payload, null);
    }

    public static <T> ApiResponse<T> error(int statusCode, String errorMessage) {
        return new ApiResponse<>(statusCode, null, null, errorMessage);
    }

}