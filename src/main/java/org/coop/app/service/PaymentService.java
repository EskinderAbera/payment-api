package org.coop.app.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.coop.app.client.ThirdPartyPaymentService;
import org.coop.app.dto.PaymentRequest;
import org.coop.app.entity.CreditStatus;
import org.coop.app.models.CreditTransaction;
import org.coop.app.models.Payment;
import org.coop.app.repository.CreditTransactionRepository;
import org.coop.app.repository.PaymentRepository;
import org.coop.app.entity.PaymentStatus;
import org.jboss.logging.Logger;
import io.smallrye.mutiny.Uni;


import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PaymentService {

    private static final Logger LOG = Logger.getLogger(PaymentService.class);

    @Inject
    PaymentRepository paymentRepository;

    @Inject
    CreditTransactionRepository creditTransactionRepository;

    @Inject
    ThirdPartyPaymentService thirdPartyPaymentService;

    @Transactional
    public Uni<Payment> savePayment(PaymentRequest request) {
        LOG.info("Starting payroll payment process...");

        // Validate sufficient balance
//        if (request.getBalance() < request.getTotalAmount()) {
//            throw new WebApplicationException("Insufficient balance", 400);
//        }

        Payment payment = new Payment();
        payment.setDebitAccount(request.getDebitAccount());
        payment.setBankCode(request.getBankCode());
        payment.setAccountHolderName(request.getAccountHolderName());
        payment.setBalance(request.getBalance());
        payment.setTotalAmount(request.getTotalAmount());
        payment.setStatus(PaymentStatus.PENDING);

        paymentRepository.persist(payment);

        List<CreditTransaction> creditTransactions = request.getCreditTransactions().stream()
                .map(ct -> {
                    CreditTransaction transaction = new CreditTransaction();
                    transaction.setPayment(payment);
                    transaction.setCreditAccount(ct.getCreditAccount());
                    transaction.setAmount(ct.getAmount());
                    transaction.setStatus(CreditStatus.PENDING);
                    return transaction;
                })
                .collect(Collectors.toList());


        creditTransactionRepository.persist(creditTransactions);

        //Call the esb api

        return thirdPartyPaymentService.initiateTransfer(payment, creditTransactions)
                .onItem().transform(success -> {
                    if (success) {
                        LOG.info("Third-party payment successful, updating statuses...");
                        payment.setStatus(PaymentStatus.COMPLETED);
                        creditTransactions.forEach(ct -> ct.setStatus(CreditStatus.COMPLETED));
                    } else {
                        LOG.warn("Third-party payment failed, rolling back...");
                        payment.setStatus(PaymentStatus.FAILED);
                        creditTransactions.forEach(ct -> ct.setStatus(CreditStatus.FAILED));
                    }
                    return payment;
                })
                .onFailure().recoverWithItem(error -> {
                    LOG.error("Error during third-party API call: " + error.getMessage(), error);
                    payment.setStatus(PaymentStatus.FAILED);
                    creditTransactions.forEach(ct -> ct.setStatus(CreditStatus.FAILED));
                    return payment;
                });
    }
}
