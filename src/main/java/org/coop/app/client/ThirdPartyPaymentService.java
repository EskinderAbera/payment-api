package org.coop.app.client;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.coop.app.models.CreditTransaction;
import org.coop.app.models.Payment;
import org.jboss.logging.Logger;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import java.util.List;

@ApplicationScoped
public class ThirdPartyPaymentService {

    private static final Logger LOG = Logger.getLogger(ThirdPartyPaymentService.class);

    @CircuitBreaker(requestVolumeThreshold = 5, failureRatio = 0.5, delay = 10000)
    @Retry(maxRetries = 3, delay = 2000)
//    @Timeout(value = 5)
    public Uni<Boolean> initiateTransfer(Payment payment, List<CreditTransaction> transactions) {
        LOG.info("Calling external payment API asynchronously...");

        return Uni.createFrom().item(() -> {
            try {
                Thread.sleep(2000); // Simulating API delay
                LOG.info("API Call successful: Transaction ID XYZ123");
                return true;
            } catch (Exception e) {
                LOG.error("API Call failed: " + e.getMessage(), e);
                throw new RuntimeException("Third-party API failed");
            }
        });
    }
}
