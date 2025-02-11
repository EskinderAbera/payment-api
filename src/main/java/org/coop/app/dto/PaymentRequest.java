package org.coop.app.dto;


import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;
import jakarta.validation.constraints.*;

@Getter
@Setter
public class PaymentRequest {

    @NotBlank(message = "Debit account cannot be empty")
    private String debitAccount;

    @NotBlank(message = "Bank Code cannot be empty")
    private String bankCode;

    private String accountHolderName;

    @NotNull(message = "Balance cannot be empty")
    private BigDecimal balance;

    @NotNull(message = "Total amount cannot be null")
    @Positive(message = "Total amount must be positive")
    private BigDecimal totalAmount;

    @Size(min = 1, message = "At least one credit transaction is required")
    private List<CreditTransactionRequest> creditTransactions;
}
