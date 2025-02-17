package org.coop.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.coop.app.client.ThirdPartyPaymentService;
import org.coop.app.dto.ApiResponse;
import org.coop.app.dto.PaymentRequest;
import org.coop.app.models.Payment;
import org.coop.app.repository.PaymentRepository;
import org.coop.app.entity.PaymentStatus;
import org.jboss.logging.Logger;

import java.math.BigDecimal;
import java.util.Map;

@ApplicationScoped
public class PaymentService {

    private static final Logger LOG = Logger.getLogger(PaymentService.class);

    @Inject
    CustomerService customerService;

    @Inject
    PaymentRepository paymentRepository;

    @Inject
    ThirdPartyPaymentService thirdPartyPaymentService;

    @Transactional
    public Uni<ApiResponse<?>> savePayment(PaymentRequest request) {
        LOG.info("Starting payroll payment process...");

        return Uni.createFrom().item(() -> {
                    try {
                        return customerService.fetchCustomerFromCore(request.getDebitAccount());
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Failed to process customer data", e);
                    }
                })
                .onItem().transformToUni(coreCustomer -> coreCustomer
                        .map(customerData -> processPayment(request, customerData))
                        .orElseGet(() -> {
                            LOG.warn("Customer not found, aborting payment.");
                            return Uni.createFrom().item(ApiResponse.error(404, "Customer not found"));
                        }))
                .onFailure().recoverWithItem(e -> {
                    LOG.error("Error processing payment", e);
                    return ApiResponse.error(500, "Internal server error");
                });
    }

    private Uni<ApiResponse<?>> processPayment(PaymentRequest request, Map<String, Object> customerData) {
        BigDecimal availableBalance = convertToBigDecimal(customerData.get("availableBalance"));
        BigDecimal totalAmount = request.getTotalAmount();

        if (availableBalance.compareTo(totalAmount) < 0) {
            return Uni.createFrom().item(ApiResponse.error(400, "Insufficient balance"));
        }

        Payment payment = new Payment();
        payment.setDebitAccount(request.getDebitAccount());
        payment.setBankCode(request.getBankCode());
        payment.setTotalAmount(totalAmount);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setBalance(availableBalance);
        payment.setAccountHolderName((String) customerData.get("customerName"));

        paymentRepository.persist(payment);

        return Uni.createFrom().item(ApiResponse.success(payment, "Payment initiated successfully"));

//        return Uni.createFrom().item(payment)
//                .onItem().transformToUni(p -> paymentRepository.persist(p))
//                .onItem().transform(p -> ApiResponse.success(p, "Payment initiated successfully"))
//                .onFailure().recoverWithItem(e -> {
//                    LOG.error("Failed to persist payment", e);
//                    return ApiResponse.error(500, "Failed to persist payment");
//                });
    }

    private BigDecimal convertToBigDecimal(Object value) {
        if (value instanceof Double) {
            return BigDecimal.valueOf((Double) value);
        } else if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        throw new IllegalArgumentException("Unsupported type for balance: " + value.getClass().getName());
    }
}