package org.coop.app.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.coop.app.models.CreditTransaction;
import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.coop.app.models.Payment;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CreditTransactionRepository implements PanacheRepository<CreditTransaction> {

    @Inject
    JPAStreamer jpaStreamer;

    public List<CreditTransaction> findByPayment(Payment payment) {
        return jpaStreamer.stream(CreditTransaction.class)
                .filter(ct -> ct.getPayment().equals(payment))
                .toList();
    }
    public CreditTransaction findById(UUID id) {
        return find("id", id).firstResult();
    }

//    public CreditTransaction findById(UUID uuid) {
//        return jpaStreamer.stream(CreditTransaction.class)
//                .filter(ct -> ct.);
//    }
}

