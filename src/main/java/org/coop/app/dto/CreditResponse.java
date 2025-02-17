package org.coop.app.dto;

public class CreditResponse {
    private String creditAccount;
    private String status;

    public CreditResponse(String creditAccount, String status) {
        this.creditAccount = creditAccount;
        this.status = status;
    }

    public String getCreditAccount() {
        return creditAccount;
    }

    public String getStatus() {
        return status;
    }
}
