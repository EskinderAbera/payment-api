package org.coop.app.entity;

public enum PaymentStatus {
    PENDING,    // Payment is created but not yet processed
    PROCESSING, // Payment is currently being processed
    COMPLETED,  // Payment is fully processed (debit & all credits succeeded)
    FAILED      // Payment failed (one or more credits didn't go through)
}
