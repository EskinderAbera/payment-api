//package org.coop.app.models;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.coop.app.entity.PaymentStatus;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.GenericGenerator;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.util.UUID;
//
//@Entity
//@Table(name = "payments")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Payment {
//    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
//    @Column(name = "id", updatable = false, nullable = false)
//    private UUID id;
//
//
//    @Column(name = "debitAccount", unique = true, nullable = false)
//    private String debitAccount;
//
//    @Column(name = "bank_code", nullable = false)
//    private String bankCode;
//
//    @Column(name = "account_holder_name")
//    private String accountHolderName;
//
//    @Column(name = "balance", nullable = false)
//    private BigDecimal balance;
//
//
//    @Column(name = "totalAmount", nullable = false)
//    private BigDecimal totalAmount;
//
//    @Column(name = "created_at", updatable = false)
//    @CreationTimestamp
//    private Timestamp createdAt;
//
//    @Column(name = "updated_at")
//    @UpdateTimestamp
//    private Timestamp updatedAt;
//
//    @Enumerated(EnumType.STRING)
//    private PaymentStatus status;
//}

package org.coop.app.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.coop.app.entity.PaymentStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "debit_account", nullable = false)
    private String debitAccount;

    @Column(name = "bank_code", nullable = false)
    private String bankCode;

    @Column(name = "account_holder_name")
    private String accountHolderName;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;
}