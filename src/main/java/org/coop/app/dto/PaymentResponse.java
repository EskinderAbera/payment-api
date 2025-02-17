package org.coop.app.dto;

import java.util.List;

public class PaymentResponse {
    private String message;
    private List<CreditResponse> creditResponse;

    public PaymentResponse(String message, List<CreditResponse> creditResponse) {
        this.message = message;
        this.creditResponse = creditResponse;
    }

    public String getMessage() {
        return message;
    }

    public List<CreditResponse> getCreditResponse() {
        return creditResponse;
    }
}
