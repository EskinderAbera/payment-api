package org.coop.app.dto;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;
import jakarta.validation.constraints.*;


@Getter
@Setter
public class BulkTransferRequest {
    @NotBlank(message = "Debit account cannot be empty")
    private String debitAccount;

    @NotNull(message = "Total amount cannot be null")
    @Positive(message = "Total amount must be positive")
    private BigDecimal totalAmount;

    @NotBlank(message = "BulkId cannot be empty")
    private String bulkId;

    @Size(min = 1, message = "At least one credit transaction is required")
    @Valid
    private List<CreditTransactionDetail> creditTransactions;
}
