package org.coop.app.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class CreditTransactionRequest {

    @NotBlank(message = "Credit account cannot be empty")
    private String creditAccount;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "isWallet cannot be null")
    private Boolean isWallet;
}

