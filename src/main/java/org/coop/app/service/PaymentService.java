package org.coop.app.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ProcessingException;
import lombok.Value;
import org.coop.app.dto.*;
import org.coop.app.entity.CreditStatus;
import org.coop.app.entity.PaymentStatus;
import org.coop.app.exception.BulkTransferException;
import org.coop.app.models.Payment;
import org.coop.app.models.CreditTransaction;
import org.coop.app.repository.PaymentRepository;
import org.coop.app.repository.CreditTransactionRepository;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class PaymentService {

    @ConfigProperty(name = "api.key")
    String apiKey;

    @Inject
    PaymentRepository paymentRepository;

    @Inject
    CreditTransactionRepository creditTransactionRepository;

    @Inject
    @RestClient
    BulkTransferService bulkTransferService;
    @Inject
    CustomerService customerService;

    @Transactional
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
//        String apiKey = "ddd4076fb15088839ce13f187fa32eeddd3e889e8f016763440bccbf085c54d41bed779d36348b633cc29a5ef114ba85adb6ce669fcc517266a058185ae57efc";

        // Create and save Payment
        Payment payment = new Payment();
        payment.setDebitAccount(paymentRequest.getDebitAccount());
        payment.setBankCode(paymentRequest.getBankCode());
        payment.setAccountHolderName(paymentRequest.getAccountHolderName());
        payment.setBalance(paymentRequest.getBalance());
        payment.setTotalAmount(paymentRequest.getTotalAmount());
        payment.setStatus(PaymentStatus.PENDING);
        paymentRepository.persist(payment);

        // Create and save CreditTransactions
        List<CreditTransaction> creditTransactions = paymentRequest.getCreditTransactions().stream()
                .map(ctRequest -> {
                    CreditTransaction ct = new CreditTransaction();
                    ct.setPayment(payment);
                    ct.setCreditAccount(ctRequest.getCreditAccount());
                    ct.setAmount(ctRequest.getAmount());
                    ct.setStatus(CreditStatus.PENDING);
                    return ct;
                }).collect(Collectors.toList());
        creditTransactionRepository.persist(creditTransactions);

        // Prepare BulkTransferRequest
        BulkTransferRequest bulkTransferRequest = new BulkTransferRequest();
        bulkTransferRequest.setDebitAccount(payment.getDebitAccount());
        bulkTransferRequest.setTotalAmount(payment.getTotalAmount());
        bulkTransferRequest.setBulkId(payment.getId().toString());
        bulkTransferRequest.setCreditTransactions(creditTransactions.stream()
                .map(ct -> {
                    CreditTransactionDetail ctd = new CreditTransactionDetail();
                    ctd.setOrderId(ct.getId().toString());
                    ctd.setCreditAccount(ct.getCreditAccount());
                    ctd.setAmount(ct.getAmount());
                    return ctd;
                }).collect(Collectors.toList()));
        try {
            BulkTransferResponse bulkTransferResponse = bulkTransferService.processBulkTransfer(apiKey, bulkTransferRequest);

            // Process response
            return updateTransactionStatuses(bulkTransferResponse);

        } catch (ProcessingException e) {
            Throwable cause = e.getCause();
            if (cause instanceof SocketTimeoutException) {
                throw new BulkTransferException("Bulk transfer request timed out", e);
            }
            throw new BulkTransferException("Failed to process bulk transfer", e);
        }
    }

    @Transactional
    public PaymentResponse updateTransactionStatuses(BulkTransferResponse bulkTransferResponse) {
        List<CreditResponse> creditResponses = bulkTransferResponse.getTransactionStatuses().stream()
                .map(ts -> {
                    CreditTransaction ct = creditTransactionRepository.findById(UUID.fromString(ts.getOrderId()));
                    if (ts.getStatus().equals("SUCCESS")) {
                        ct.setStatus(CreditStatus.COMPLETED);
                        ct.setTransactionId(ts.getTransactionId());
                    } else {
                        ct.setStatus(CreditStatus.FAILED);
                        ct.setTransactionId(ts.getMessage());
                    }
                    creditTransactionRepository.persist(ct);
                    return new CreditResponse(ct.getCreditAccount(), ct.getStatus().name());
                })
                .collect(Collectors.toList());

        return new PaymentResponse("Success", creditResponses);
    }

}