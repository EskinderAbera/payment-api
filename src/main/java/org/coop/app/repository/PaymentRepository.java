//package org.coop.app.repository;
//
//import io.quarkus.hibernate.orm.panache.PanacheRepository;
//import jakarta.transaction.Transactional;
//import org.coop.app.models.Payment;
//import com.speedment.jpastreamer.application.JPAStreamer;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import java.util.UUID;
//
//@ApplicationScoped
//public class PaymentRepository implements PanacheRepository<Payment> {
//
//    @Inject
//    JPAStreamer jpaStreamer;
//
//    public Payment findById(UUID id) {
//        return jpaStreamer.stream(Payment.class)
//                .filter(p -> p.getId().equals(id))
//                .findFirst()
//                .orElse(null);
//    }
//
//}
//

package org.coop.app.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.coop.app.models.Payment;

import java.util.UUID;

@ApplicationScoped
public class PaymentRepository implements PanacheRepository<Payment> {
    public Payment findByDebitAccount(String debitAccount) {
        return find("debitAccount", debitAccount).firstResult();
    }
}
