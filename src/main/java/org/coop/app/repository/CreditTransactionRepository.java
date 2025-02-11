package org.coop.app.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.coop.app.models.CreditTransaction;
import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.coop.app.models.Payment;

import java.util.List;

@ApplicationScoped
public class CreditTransactionRepository implements PanacheRepository<CreditTransaction> {

    @Inject
    JPAStreamer jpaStreamer;

    public List<CreditTransaction> findByPayment(Payment payment) {
        return jpaStreamer.stream(CreditTransaction.class)
                .filter(ct -> ct.getPayment().equals(payment))
                .toList();
    }

}

