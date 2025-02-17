package org.coop.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionStatus {
    private String orderId;
    private String status;
    private String message;
    private String transactionId;

    // Getters and Setters
}
