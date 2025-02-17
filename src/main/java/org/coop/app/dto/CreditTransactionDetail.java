package org.coop.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreditTransactionDetail {

    @NotBlank(message = "BulkId cannot be empty")
    private String bulkId;

    @NotBlank(message = "OrderId cannot be empty")
    private String orderId;

    @NotBlank(message = "Credit account cannot be empty")
    private String creditAccount;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
}
