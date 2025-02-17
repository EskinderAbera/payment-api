package org.coop.app.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.coop.app.entity.CreditStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "credit_transactions")
public class CreditTransaction {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    private String creditAccount;

    private String transactionId;

    private String message;

    private BigDecimal amount;

    private Boolean isWallet;

    @Enumerated(EnumType.STRING)
    private CreditStatus status;
}
