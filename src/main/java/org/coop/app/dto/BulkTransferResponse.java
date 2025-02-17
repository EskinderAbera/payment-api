package org.coop.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BulkTransferResponse {
    private String message;
    private List<TransactionStatus> transactionStatuses;

    // Getters and Setters
}
